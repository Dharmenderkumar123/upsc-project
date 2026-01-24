package com.cmt.viewModel.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CurrentAffairsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PageNation
import com.cmt.view.activity.PlainActivity

class CurrentAffairsViewVM : ViewModel() {
    val currentAffairsList: MutableLiveData<PageNation<*>> = MutableLiveData()
    var noData: MutableLiveData<Boolean> = MutableLiveData()
    var errorMsg: MutableLiveData<String> = MutableLiveData()

    fun setData(context: Context, id: String?, pageCount: String) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.category_id] = id.toString()
        params[IConstants.Params.page_no] = pageCount ?: "1"
        CurrentAffairsAPI().currentAffairsList(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val data = apiResponse.data as? PageNation<*>
                        currentAffairsList.value = data!!
                    } else if (apiResponse?.error_code == IConstants.Response.invalid) {
                        noData.value = true
                        errorMsg.value = apiResponse.message!!
                    }
                }
            }

        })

    }
}