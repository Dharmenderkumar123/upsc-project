package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.MyMaterialAdapater
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.MyCoursesAPI
import com.cmt.services.api.MyMaterialsAPICall
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.MyMaterialModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentMyMaterialsBinding

class MyMaterialsVM : ViewModel() {
    lateinit var binding: FragmentMyMaterialsBinding
    var materialData: MutableLiveData<MutableList<MyMaterialModel>> = MutableLiveData()
    var noData: MutableLiveData<Boolean> = MutableLiveData()
    var noDataMsg: MutableLiveData<String> = MutableLiveData()

    init {
        noData.value = false
    }

    fun setData(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)

        MyMaterialsAPICall().MyMaterials(params, object : RetrofitCallBack {
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
                        materialData.value = dataResponse
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                        noData.value = true
                        noDataMsg.value = apiResponse?.message
                    }
                }
            }

        })
    }


}