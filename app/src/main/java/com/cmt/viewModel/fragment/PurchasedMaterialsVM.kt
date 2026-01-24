package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.MyMaterialsAPICall
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyMaterialModel
import com.cmt.view.activity.PlainActivity

class PurchasedMaterialsVM : ViewModel() {
    var materialData: MutableLiveData<MutableList<MyMaterialModel>> = MutableLiveData()

    fun setData(context: Context, subjectId: String?) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.subject_id] = subjectId.toString()
        MyMaterialsAPICall().PurchasedMaterials(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<MyMaterialModel>()
                                .toMutableList()
                        materialData.value = dataResponse!!
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }
}