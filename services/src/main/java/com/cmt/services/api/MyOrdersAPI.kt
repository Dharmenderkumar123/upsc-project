package com.cmt.services.api

import com.cmt.services.apiInterface.MyMaterialAPI
import com.cmt.services.apiInterface.MyOrders
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyMaterialModel
import com.cmt.services.model.MyOrdersModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrdersAPI {
    fun MyOrders(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: MyOrders = APIClient().getInstance().create(MyOrders::class.java)
        val call: Call<APIResponse<MutableList<MyOrdersModel>>> =
            client.myOrders(params)
        call.enqueue(object : Callback<APIResponse<MutableList<MyOrdersModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<MyOrdersModel>>>,
                response: Response<APIResponse<MutableList<MyOrdersModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<MyOrdersModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}