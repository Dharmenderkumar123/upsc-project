package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CoursesSubCategoryAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.SubCategoriesAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.SubCourseModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentSubCourseBinding

class SubcourseVM : ViewModel() {
    lateinit var binding: FragmentSubCourseBinding

    fun setdata(view: View, model: Courses, type: String) {
        val activity = view.context as? FullPlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        model.category_id.toString().let {
            params[IConstants.Params.category_id] = it
        }
        SubCategoriesAPI().subCategories(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<SubCourseModel>()
                                .toMutableList()
                        binding.recyclerView.apply {
                            adapter =
                                CoursesSubCategoryAdapter(binding.root.context, dataResponse, type)
                        }

                    } else {
                        binding.recyclerView.setVisibility(View.GONE)
                        binding.tvNoCourse.setVisibility(View.VISIBLE)
                    }
                }
            }
        })
    }


    fun buyNow(view: View) {
        val intent = Intent(view.context, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.BuyPlan)
        intent.putExtra(IConstants.IntentStrings.payload, "Buy Plan")
        view.context.startActivity(intent)
        (view.context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
    }

}