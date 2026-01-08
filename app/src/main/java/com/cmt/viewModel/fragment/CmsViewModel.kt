package com.cmt.viewModel.fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CmsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.CMSModel
import com.cmt.view.activity.PlainActivity

class CmsViewModel : ViewModel() {
    val data: MutableLiveData<CMSModel> = MutableLiveData()

    fun aboutUsAPI(view: View) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        CmsAPI().aboutUs(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? CMSModel
                        data.value = dataResponse
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })

    }

    fun termsAPI(view: View) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        CmsAPI().terms(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? CMSModel
                        data.value = dataResponse
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })

    }
}