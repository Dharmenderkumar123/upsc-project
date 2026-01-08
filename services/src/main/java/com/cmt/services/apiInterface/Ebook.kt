package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.EbookCourseSubjectModel

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Ebook {
    @FormUrlEncoded
    @POST(IServiceConstants.API.ebooks)
    fun ebooksList(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<EbookCourseSubjectModel>>>
}