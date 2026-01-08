package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubjectsModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyCoursesSubjects {
    @FormUrlEncoded
    @POST(IServiceConstants.API.Course_subjects)
    fun myCoursesSubjects(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<SubjectsModel>>>
}