package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.CurrentAffairsListModel
import com.cmt.services.model.CurrentAffairsViewModel
import com.cmt.services.model.PageNation
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CurrentAffairs {

    @GET("categories")
    fun getCurrentAffairs(): Call<APIResponse<MutableList<CurrentAffairsListModel>>>

    @FormUrlEncoded
    @POST("current_affairs")
    fun currentAffairsList(@FieldMap params: HashMap<String, String>): Call<APIResponse<PageNation<MutableList<CurrentAffairsViewModel>>>>
}