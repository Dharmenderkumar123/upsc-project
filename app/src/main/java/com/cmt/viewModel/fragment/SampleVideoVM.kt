package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.Packages
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.databinding.FragmentSampleVideosBinding

class SampleVideoVM : ViewModel() {
    lateinit var binding: FragmentSampleVideosBinding
    var packageData: MutableLiveData<PackageDetailsModel> = MutableLiveData()
    fun setdata(view: View, model: AgricatCategoryModel) {
        val activity = view.context as? FullPlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        model.subject_id.toString().let {
            params[IConstants.Params.subject_id] = it
        }
        params[IConstants.Params.user_id] = AppPreferences().getUserId(view.context)
        Packages().packageDetails(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as PackageDetailsModel
                        packageData.value = dataResponse!!
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })

    }

}