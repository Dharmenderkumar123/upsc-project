package com.cmt.services.api

import com.cmt.services.apiInterface.CurrentAffairs
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.CurrentAffairsListModel
import com.cmt.services.model.CurrentAffairsViewModel
import com.cmt.services.model.PageNation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentAffairsAPI {

    fun getCurrentAffairs(retrofitCallBack: RetrofitCallBack) {
        val client: CurrentAffairs = APIClient().getInstance().create(CurrentAffairs::class.java)
        val call: Call<APIResponse<MutableList<CurrentAffairsListModel>>> =
            client.getCurrentAffairs()
        call.enqueue(object : Callback<APIResponse<MutableList<CurrentAffairsListModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<CurrentAffairsListModel>>>,
                response: Response<APIResponse<MutableList<CurrentAffairsListModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<CurrentAffairsListModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun currentAffairsList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: CurrentAffairs = APIClient().getInstance().create(CurrentAffairs::class.java)
        val call: Call<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>> =
            client.currentAffairsList(params)
        call.enqueue(object :
            Callback<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>> {
            override fun onResponse(
                call: Call<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>>,
                response: Response<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}