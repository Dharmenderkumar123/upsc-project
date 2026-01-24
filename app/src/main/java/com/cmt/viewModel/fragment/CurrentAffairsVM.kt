package com.cmt.viewModel.fragment

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CurrentAffairsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.CurrentAffairsListModel
import com.cmt.services.model.SubjectsListModel
import com.cmt.view.activity.PlainActivity

class CurrentAffairsVM : ViewModel() {
    val currentAffairsData: MutableLiveData<MutableList<CurrentAffairsListModel>> =
        MutableLiveData()
    var noData: MutableLiveData<Boolean> = MutableLiveData()
    var errorMsg: MutableLiveData<String> = MutableLiveData()

    fun setCurrentAffairsData(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        CurrentAffairsAPI().getCurrentAffairs(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<CurrentAffairsListModel>()
                                .toMutableList()
                        currentAffairsData.value = dataResponse!!
                    } else {
                        noData.value = true
                        errorMsg.value = apiResponse?.message!!
                    }
                }
            }

        })
    }
}