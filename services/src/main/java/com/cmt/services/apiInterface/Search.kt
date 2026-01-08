package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.MyCourseModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Search {
    @FormUrlEncoded
    @POST(IServiceConstants.API.search)
    fun searchResult(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<AgricatCategoryModel>>>
}