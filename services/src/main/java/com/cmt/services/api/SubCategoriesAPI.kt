package com.cmt.services.api

import com.cmt.services.apiInterface.SubCategoriesCourse
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubCourseModel
import com.cmt.services.model.SubjectsListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoriesAPI {
    fun subCategories(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: SubCategoriesCourse =
            APIClient().getInstance().create(SubCategoriesCourse::class.java)
        val call: Call<APIResponse<MutableList<SubCourseModel>>> = client.subCategories(params)
        call.enqueue(object : Callback<APIResponse<MutableList<SubCourseModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<SubCourseModel>>>,
                response: Response<APIResponse<MutableList<SubCourseModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<SubCourseModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun subjectsList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: SubCategoriesCourse =
            APIClient().getInstance().create(SubCategoriesCourse::class.java)
        val call: Call<APIResponse<SubjectsListModel>> = client.subjectsList(params)
        call.enqueue(object : Callback<APIResponse<SubjectsListModel>> {
            override fun onResponse(
                call: Call<APIResponse<SubjectsListModel>>,
                response: Response<APIResponse<SubjectsListModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<SubjectsListModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}