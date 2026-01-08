package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.SubjectItemsAdapter
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.MyCoursesAPI
import com.cmt.services.api.MyCoursesSubjectsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubjectsModel
import com.cmt.services.model.MyCourseModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentSubjectsBinding

class SubjectsVM : ViewModel() {
    lateinit var binding: FragmentSubjectsBinding
    var subjectsData: MutableLiveData<MutableList<SubjectsModel>> = MutableLiveData()

    @SuppressLint("SetTextI18n")
    fun setData(model: MyCourseModel, context: Context) {
        binding.courseName.text = model.title
        binding.btnStatus.text = "Status : " + model.status
        binding.tvExpire.text = "Expiry Date " + model.expiry_date
        binding.tvExp.text = "Expiry Date " + model.expiry_date

        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.course_id] = model.course_id.toString()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
        MyCoursesSubjectsAPI().myPurchasedCoursesSubjects(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<SubjectsModel>()
                                .toMutableList()
                        subjectsData.value = dataResponse
                    } else {
                        binding.tvNoData.isVisible = true
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                        binding.tvNoData.text = apiResponse?.message
                    }
                }
            }

        })

    }
}