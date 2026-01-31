package com.cmt.services.api

import com.cmt.services.apiInterface.BuyPlanInterface
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackagesModel
import com.cmt.services.model.PaymentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyPlanApi {
    fun getPlans(hm: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {

        val client: BuyPlanInterface = APIClient().getInstance().create(BuyPlanInterface::class.java)

        val call: Call<APIResponse<MutableList<PackagesModel>>> = client.getPackages(hm)

        call.enqueue(object : Callback<APIResponse<MutableList<PackagesModel>>> {
            override fun onResponse(call: Call<APIResponse<MutableList<PackagesModel>>>, response: Response<APIResponse<MutableList<PackagesModel>>>) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<MutableList<PackagesModel>>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }
        })
    }

    fun getPayementInfo(retrofitCallBack: RetrofitCallBack) {

        val client: BuyPlanInterface = APIClient().getInstance().create(BuyPlanInterface::class.java)

        val call: Call<APIResponse<PaymentModel>> = client.getPaymentDetails()

        call.enqueue(object : Callback<APIResponse<PaymentModel>> {
            override fun onResponse(call: Call<APIResponse<PaymentModel>>, response: Response<APIResponse<PaymentModel>>) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<PaymentModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }
        })
    }
}