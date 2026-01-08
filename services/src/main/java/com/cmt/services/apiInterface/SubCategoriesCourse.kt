package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubCourseModel
import com.cmt.services.model.SubjectsListModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SubCategoriesCourse {
    @FormUrlEncoded
    @POST(IServiceConstants.API.Course_categories)
    fun subCategories(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<SubCourseModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.packages)
    fun subjectsList(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<SubjectsListModel>>
}