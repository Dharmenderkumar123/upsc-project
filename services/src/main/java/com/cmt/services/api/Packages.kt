package com.cmt.services.api

import com.cmt.services.apiInterface.PackageDetails
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackageDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Packages {
    fun packageDetails(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: PackageDetails =
            APIClient().getInstance().create(PackageDetails::class.java)
        val call: Call<APIResponse<PackageDetailsModel>> = client.packageDetails(params)
        call.enqueue(object : Callback<APIResponse<PackageDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<PackageDetailsModel>>,
                response: Response<APIResponse<PackageDetailsModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<PackageDetailsModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}