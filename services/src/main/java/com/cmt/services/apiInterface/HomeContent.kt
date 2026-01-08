package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.http.GET

interface HomeContent {
    @GET(IServiceConstants.API.courses)
    fun getCourse(): Call<APIResponse<MutableList<Courses>>>

    @GET(IServiceConstants.API.banners)
    fun getBanners(): Call<APIResponse<MutableList<HomeAdvBannerModel>>>

    @GET(IServiceConstants.API.ebooks)
    fun getECourses(): Call<APIResponse<MutableList<EbookDataModel>>>

}