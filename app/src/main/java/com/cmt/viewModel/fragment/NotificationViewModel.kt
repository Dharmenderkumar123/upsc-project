package com.cmt.viewModel.fragment

import android.app.Application
import android.provider.ContactsContract
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CmsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.NotificationDataModel
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.activity.BaseViewModel
import com.the_pride_ias.databinding.FragmentNotificationBinding

class NotificationViewModel(application: Application) : BaseViewModel(application) {
    lateinit var binding: FragmentNotificationBinding
    private val notificationData: MutableLiveData<MutableList<NotificationDataModel>> =
        MutableLiveData()
    var noData: MutableLiveData<Boolean> = MutableLiveData()
    var noDataMsg: MutableLiveData<String> = MutableLiveData()

    init {
        noData.value = false
    }

    fun getNotificationDataObserver(): MutableLiveData<MutableList<NotificationDataModel>> {
        return notificationData
    }

    fun setNotificationData() {
        val activity = binding.root.context as? PlainActivity
        activity?.activityLoader(true)
        val params = HashMap<String, String>()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity!!)
        CmsAPI().notifications(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                if (error != null) {
                    activity.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<NotificationDataModel>()
                                .toMutableList()
                        notificationData.postValue(dataResponse)
                    } else {
                        noData.value = true
                        noDataMsg.value = "No Notifications found"
                        //apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }
}