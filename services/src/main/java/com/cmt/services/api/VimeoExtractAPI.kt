package com.cmt.services.api

import com.cmt.services.apiInterface.ViemoInterface
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.VimeoResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VimeoExtractAPI {
    fun getVimeoVideoUrl(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: ViemoInterface = APIClient().vimeoInstance().create(ViemoInterface::class.java)
        val call: Call<ResponseBody> = client.getVimeoUrlResponse(params)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val responseStr = response.body()?.bytes()?.let { String(it) }
                val jsonObject = JSONObject(responseStr)
                retrofitCallBack.responseListener(jsonObject)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}