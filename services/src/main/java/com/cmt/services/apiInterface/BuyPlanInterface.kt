package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackagesModel
import com.cmt.services.model.PaymentModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BuyPlanInterface {

    @GET(IServiceConstants.API.getPackages)
    fun getPackages(@QueryMap hm: HashMap<String, String>): Call<APIResponse<MutableList<PackagesModel>>>

    @GET(IServiceConstants.API.paymentDetails)
    fun getPaymentDetails(): Call<APIResponse<PaymentModel>>

}