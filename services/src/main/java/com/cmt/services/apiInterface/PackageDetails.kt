package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackageDetailsModel
import com.cmt.services.model.SubCourseModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PackageDetails {
    @FormUrlEncoded
    @POST(IServiceConstants.API.package_details)
    fun packageDetails(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<PackageDetailsModel>>
}