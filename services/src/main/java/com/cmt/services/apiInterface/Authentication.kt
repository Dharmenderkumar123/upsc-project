package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.RegisterResponseModel
import com.cmt.services.model.UserDetailsModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Authentication {

    @FormUrlEncoded
    @POST(IServiceConstants.API.register)
    fun register(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<RegisterResponseModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.verify_otp)
    fun verify_otp(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.login)
    fun login(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.change_password)
    fun change_password(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.version_check)
    fun version_check(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.resend_otp)
    fun resendOtp(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.forgot_password)
    fun forgotPassword(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<RegisterResponseModel>>
}