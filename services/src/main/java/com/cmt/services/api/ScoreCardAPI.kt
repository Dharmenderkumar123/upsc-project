package com.cmt.services.api

import com.cmt.services.apiInterface.ScoreCard
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubmitedAnswerModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScoreCardAPI {
    fun score(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: ScoreCard =
            APIClient().getInstance().create(ScoreCard::class.java)
        val call: Call<APIResponse<SubmitedAnswerModel>> = client.score(params)
        call.enqueue(object : Callback<APIResponse<SubmitedAnswerModel>> {
            override fun onResponse(
                call: Call<APIResponse<SubmitedAnswerModel>>,
                response: Response<APIResponse<SubmitedAnswerModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<SubmitedAnswerModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}