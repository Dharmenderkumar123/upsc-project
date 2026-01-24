package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.Packages
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.MockTestModel
import com.cmt.services.model.PackageDetailsModel
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.databinding.FragmentMockTestBinding

class MockTestVM : ViewModel() {
    lateinit var binding: FragmentMockTestBinding
    var packageData: MutableLiveData<PackageDetailsModel> = MutableLiveData()
    fun setData(context: Context, model: AgricatCategoryModel) {
        val activity = context as? FullPlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        model.subject_id.toString().let {
            params[IConstants.Params.subject_id] = it
        }
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
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