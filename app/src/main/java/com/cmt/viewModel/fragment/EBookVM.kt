package com.cmt.viewModel.fragment

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.EbookMenuListAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.HomeContentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.EbookDataModel
import com.cmt.services.model.SubjectsListModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity

class EBookVM : ViewModel() {
    var ebookData: MutableLiveData<MutableList<Courses>> = MutableLiveData()

    fun setdata(context: Context) {
        if (context is MainActivity) {
            (context as? MainActivity)?.activityLoader(true)
        } else {
            (context as? PlainActivity)?.activityLoader(true)
        }
        HomeContentAPI().courseCategories(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (context is MainActivity) {
                    (context as? MainActivity)?.activityLoader(false)
                } else {
                    (context as? PlainActivity)?.activityLoader(false)
                }
                if (error != null) {
                    if (context is MainActivity) {
                        (context as? MainActivity)?.setSnackBar(error)
                    } else {
                        (context as? PlainActivity)?.setSnackBar(error)
                    }
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<Courses>()
                                .toMutableList()
                        ebookData.value = dataResponse!!
                    } else {
                        apiResponse?.message?.let {
                            if (context is MainActivity) {
                                (context as? MainActivity)?.setSnackBar(it)
                            } else {
                                (context as? PlainActivity)?.setSnackBar(it)
                            }
                        }
                    }

                }
            }

        })

    }
}