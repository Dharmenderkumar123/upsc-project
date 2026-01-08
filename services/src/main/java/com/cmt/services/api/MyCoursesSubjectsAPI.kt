package com.cmt.services.api

import com.cmt.services.apiInterface.MyCoursesSubjects
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubjectsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCoursesSubjectsAPI {
    fun myPurchasedCoursesSubjects(
        params: HashMap<String, String>,
        retrofitCallBack: RetrofitCallBack
    ) {
        val client: MyCoursesSubjects =
            APIClient().getInstance().create(MyCoursesSubjects::class.java)
        val call: Call<APIResponse<MutableList<SubjectsModel>>> = client.myCoursesSubjects(params)
        call.enqueue(object : Callback<APIResponse<MutableList<SubjectsModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<SubjectsModel>>>,
                response: Response<APIResponse<MutableList<SubjectsModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<SubjectsModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}