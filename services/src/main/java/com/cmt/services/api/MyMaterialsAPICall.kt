package com.cmt.services.api

import com.cmt.services.apiInterface.MyMaterialAPI
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyMaterialModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMaterialsAPICall {
    fun MyMaterials(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: MyMaterialAPI = APIClient().getInstance().create(MyMaterialAPI::class.java)
        val call: Call<APIResponse<MutableList<MyMaterialModel>>> =
            client.myMaterials(params)
        call.enqueue(object : Callback<APIResponse<MutableList<MyMaterialModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<MyMaterialModel>>>,
                response: Response<APIResponse<MutableList<MyMaterialModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<MyMaterialModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun PurchasedMaterials(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: MyMaterialAPI = APIClient().getInstance().create(MyMaterialAPI::class.java)
        val call: Call<APIResponse<MutableList<MyMaterialModel>>> =
            client.purchasedMaterials(params)
        call.enqueue(object : Callback<APIResponse<MutableList<MyMaterialModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<MyMaterialModel>>>,
                response: Response<APIResponse<MutableList<MyMaterialModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<MyMaterialModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}