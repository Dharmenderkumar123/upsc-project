package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CoursesSubCategoryAdapter
import com.cmt.adapter.EbookCourseSubjectAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.EbookAPI
import com.cmt.services.api.SubCategoriesAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.EbookCourseSubjectModel
import com.cmt.services.model.SubCourseModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentEbookCourseSubjectsBinding

class EbookCourseSubjectVM : ViewModel() {
    lateinit var binding: FragmentEbookCourseSubjectsBinding

    fun setData(view: View, model: SubCourseModel) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        model.category_id.toString().let {
            params[IConstants.Params.category_id] = it
        }
        model.sub_category_id.toString().let {
            params[IConstants.Params.sub_category_id] = it
        }
        EbookAPI().eBookList(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<EbookCourseSubjectModel>()
                                .toMutableList()
                        binding.recycleView.apply {
                            adapter = EbookCourseSubjectAdapter(binding.root.context, dataResponse)
                        }

                    } else {
                        binding.recycleView.setVisibility(View.GONE)
                        binding.tvNomaterials.setVisibility(View.VISIBLE)
                    }
                }
            }
        })


        /*val data = mutableListOf<EbookCourseSubjectModel>()
        binding.recycleView.apply {
            adapter = EbookCourseSubjectAdapter(binding.root.context, data)

        }*/
    }
}