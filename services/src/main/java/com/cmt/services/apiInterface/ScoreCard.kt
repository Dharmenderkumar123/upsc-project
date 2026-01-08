package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.SubmitedAnswerModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ScoreCard {
    @FormUrlEncoded
    @POST(IServiceConstants.API.view_result)
    fun score(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<SubmitedAnswerModel>>
}