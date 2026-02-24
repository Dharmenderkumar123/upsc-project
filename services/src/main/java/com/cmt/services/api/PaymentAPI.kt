package com.cmt.services.api

import com.cmt.services.apiInterface.Payment
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.OrderIdModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentAPI {
    fun paySuccess(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Payment = APIClient().getInstance().create(Payment::class.java)
        val call: Call<APIResponse<Any>> = client.paySuccess(params)
        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(call: Call<APIResponse<Any>>, response: Response<APIResponse<Any>>) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<Any>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun generateOrderId(params: HashMap<String, String>, retroFitCallBack: RetrofitCallBack) {
        val client: Payment = APIClient().getInstance().create(Payment::class.java)
        val call: Call<ResponseBody> = client.generateOrderId(params)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.bytes()?.let { bytes ->
                    val responseString = String(bytes)
                    val jsonObject = JSONObject(responseString)
                    retroFitCallBack.responseListener(response = jsonObject)
                } ?: retroFitCallBack.responseListener(response = null, error = null)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val error = t.message ?: "not found"
                retroFitCallBack.responseListener(response = null, error = error)
                t.printStackTrace()
            }

        })
    }
}