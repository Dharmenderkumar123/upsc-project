package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CoursesAdaptor
import com.cmt.adapter.OnlineTestsListAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.model.OnlineTestModel
import com.cmt.services.api.HomeContentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.view.activity.MainActivity
import com.the_pride_ias.databinding.FragmentOnlineTestBinding

class OnlineTestVM : ViewModel() {
    lateinit var binding: FragmentOnlineTestBinding
    fun setDataItems(view: View) {
        val activity = view.context as? MainActivity
        activity?.activityLoader(true)
        HomeContentAPI().courseCategories(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<Courses>()
                                .toMutableList()
                        binding.recycleView.apply {
                            adapter = OnlineTestsListAdapter(binding.root.context, dataResponse)

                        }

                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }

                }
            }

        })


    }
}