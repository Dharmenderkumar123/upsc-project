package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.PackageDetailsModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyCourses {
    @FormUrlEncoded
    @POST(IServiceConstants.API.my_courses)
    fun myCourses(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<MyCourseModel>>>
}