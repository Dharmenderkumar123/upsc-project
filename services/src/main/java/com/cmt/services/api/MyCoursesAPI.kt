package com.cmt.services.api

import com.cmt.services.apiInterface.MyCourses
import com.cmt.services.apiInterface.SubCategoriesCourse
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.SubjectsListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCoursesAPI {
    fun myPurchasedCourses(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: MyCourses =
            APIClient().getInstance().create(MyCourses::class.java)
        val call: Call<APIResponse<MutableList<MyCourseModel>>> = client.myCourses(params)
        call.enqueue(object : Callback<APIResponse<MutableList<MyCourseModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<MyCourseModel>>>,
                response: Response<APIResponse<MutableList<MyCourseModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<MyCourseModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}