package com.cmt.services.api

import com.cmt.services.apiInterface.TestModuleService
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestModuleAPI {

    fun questionsList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: TestModuleService =
            APIClient().getInstance().create(TestModuleService::class.java)
        val call: Call<APIResponse<MutableList<TestOptionsModel>>> = client.getQuestions(params)
        call.enqueue(object : Callback<APIResponse<MutableList<TestOptionsModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<TestOptionsModel>>>,
                response: Response<APIResponse<MutableList<TestOptionsModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<TestOptionsModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun purchasedQuestionsList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: TestModuleService =
            APIClient().getInstance().create(TestModuleService::class.java)
        val call: Call<APIResponse<PurchasedTestOptionsModel>> = client.getPurchasedQuestions(params)
        call.enqueue(object : Callback<APIResponse<PurchasedTestOptionsModel>> {
            override fun onResponse(
                call: Call<APIResponse<PurchasedTestOptionsModel>>,
                response: Response<APIResponse<PurchasedTestOptionsModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<PurchasedTestOptionsModel>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun submitAnswerList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: TestModuleService =
            APIClient().getInstance().create(TestModuleService::class.java)
        val call: Call<APIResponse<SubmitedAnswerModel>> = client.submitAnswers(params)
        call.enqueue(object : Callback<APIResponse<SubmitedAnswerModel>> {
            override fun onResponse(call: Call<APIResponse<SubmitedAnswerModel>>, response: Response<APIResponse<SubmitedAnswerModel>>) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<SubmitedAnswerModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun reviewAnswerList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: TestModuleService =
            APIClient().getInstance().create(TestModuleService::class.java)
        val call: Call<APIResponse<MutableList<ReviewAnswersModel>>> = client.reviewAnswers(params)
        call.enqueue(object : Callback<APIResponse<MutableList<ReviewAnswersModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<ReviewAnswersModel>>>,
                response: Response<APIResponse<MutableList<ReviewAnswersModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<ReviewAnswersModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}