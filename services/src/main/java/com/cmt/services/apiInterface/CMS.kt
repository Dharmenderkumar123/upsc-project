package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CMS {
    @GET(IServiceConstants.API.about_us)
    fun getAbout(): Call<APIResponse<CMSModel>>

    @GET(IServiceConstants.API.terms)
    fun getTerms(): Call<APIResponse<CMSModel>>

    @GET(IServiceConstants.API.faq)
    fun getFaq(): Call<APIResponse<MutableList<FaqModel>>>

    @GET(IServiceConstants.API.support)
    fun getSupport(): Call<APIResponse<SupportModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.contact_us)
    fun contact_us(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<Any>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.notifications)
    fun notifications(@FieldMap userParams: HashMap<String, String>):Call<APIResponse<MutableList<NotificationDataModel>>>




}