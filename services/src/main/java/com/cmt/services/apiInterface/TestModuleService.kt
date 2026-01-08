package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestModuleService {
    @FormUrlEncoded
    @POST(IServiceConstants.API.mock_test_questions)
    fun getQuestions(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<TestOptionsModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.start_exam)
    fun getPurchasedQuestions(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<PurchasedTestOptionsModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.submit_exam)
    fun submitAnswers(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<SubmitedAnswerModel>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.review_exam)
    fun reviewAnswers(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<ReviewAnswersModel>>>
}