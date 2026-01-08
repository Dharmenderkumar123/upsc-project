package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.SearchResultAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.FragmentSearchBinding

class SearchVM : ViewModel() {
    lateinit var binding: FragmentSearchBinding
    var searchData: MutableLiveData<MutableList<AgricatCategoryModel>> = MutableLiveData()
    var noData: MutableLiveData<Boolean> = MutableLiveData()
    var noDataMsg: MutableLiveData<String> = MutableLiveData()

    init {
        noData.value = false
    }

    fun setData(context: Context, searchText: String) {
        searchData.value?.clear()
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        searchText.let {
            params[IConstants.Params.search_key] = it
        }
        SearchResultAPI().searchResult(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as? MutableList<*>)?.filterIsInstance<AgricatCategoryModel>()
                                ?.toMutableList()
                        searchData.value = dataResponse
                        binding.recycleView.isVisible = true
                        noData.value = false
                    } else {
                        binding.recycleView.isVisible = false
                        noData.value = true
                        noDataMsg.value = apiResponse?.message
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })

    }

}