package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.OrderIdModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Payment {


    @FormUrlEncoded
    @POST(IServiceConstants.API.razerpayorderid)
    fun generateOrderId(@FieldMap params: HashMap<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST(IServiceConstants.API.payment_success)
    fun paySuccess(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>


}