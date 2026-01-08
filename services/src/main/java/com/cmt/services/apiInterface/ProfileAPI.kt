package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.RegisterResponseModel
import com.cmt.services.model.UserDetailsModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProfileAPI {
    @FormUrlEncoded
    @POST(IServiceConstants.API.profile)
    fun profile(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<UserDetailsModel>>

    @Multipart
    @POST(IServiceConstants.API.update_profile)
    fun update(
        @Part profileImage: MultipartBody.Part? = null,
        @PartMap data: HashMap<String, RequestBody>
    ): Call<APIResponse<Any>>
}