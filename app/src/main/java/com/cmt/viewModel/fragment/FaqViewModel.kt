package com.cmt.viewModel.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CoursesAdaptor
import com.cmt.adapter.FaqAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CmsAPI
import com.cmt.services.api.HomeContentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.FaqModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentFaqBinding

class FaqViewModel : ViewModel() {
    lateinit var binding: FragmentFaqBinding
    var enableInfo1: MutableLiveData<Boolean> = MutableLiveData()

    init {
        enableInfo1.value = false
    }

    fun setData(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        CmsAPI().faq(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<FaqModel>()
                                .toMutableList()
                        binding.recycleView.apply {
                            adapter = FaqAdapter(binding.root.context, dataResponse)
                        }

                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }

                }
            }

        })

    }

}