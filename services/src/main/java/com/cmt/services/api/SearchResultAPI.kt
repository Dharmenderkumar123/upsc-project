package com.cmt.services.api

import com.cmt.services.apiInterface.Search
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.AgricatCategoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultAPI {
    fun searchResult(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Search =
            APIClient().getInstance().create(Search::class.java)
        val call: Call<APIResponse<MutableList<AgricatCategoryModel>>> = client.searchResult(params)
        call.enqueue(object : Callback<APIResponse<MutableList<AgricatCategoryModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<AgricatCategoryModel>>>,
                response: Response<APIResponse<MutableList<AgricatCategoryModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<AgricatCategoryModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}