package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CategoryListAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.HomeContentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentCategoriesBinding

class CategoriesVM : ViewModel() {
    lateinit var binding: FragmentCategoriesBinding

    fun setCourseData(view: View) {
        val activity = view.context as? MainActivity
        if (view.context is MainActivity) {
            (view.context as? MainActivity)?.activityLoader(true)
        } else {
            (view.context as? PlainActivity)?.activityLoader(true)
        }
        HomeContentAPI().courseCategories(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (view.context is MainActivity) {
                    (view.context as? MainActivity)?.activityLoader(false)
                } else {
                    (view.context as? PlainActivity)?.activityLoader(false)
                }
                if (error != null) {
                    if (view.context is MainActivity) {
                        (view.context as? MainActivity)?.setSnackBar(error)
                    } else {
                        (view.context as? PlainActivity)?.setSnackBar(error)
                    }
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = (apiResponse.data as MutableList<*>).filterIsInstance<Courses>().toMutableList()
                        binding.recycleView.apply { adapter = CategoryListAdapter(binding.root.context, dataResponse) }
                    } else {
                        apiResponse?.message?.let {
                            if (view.context is MainActivity) {
                                (view.context as? MainActivity)?.setSnackBar(it)
                            } else {
                                (view.context as? PlainActivity)?.setSnackBar(it)
                            }
                        }
                    }

                }
            }

        })
    }
}