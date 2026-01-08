package com.cmt.services.api

import com.cmt.services.apiInterface.HomeContent
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.EbookDataModel
import com.cmt.services.model.HomeAdvBannerModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeContentAPI {
    fun courseCategories(retrofitCallBack: RetrofitCallBack) {
        val client: HomeContent = APIClient().getInstance().create(HomeContent::class.java)
        val call: Call<APIResponse<MutableList<Courses>>> = client.getCourse()
        call.enqueue(object : Callback<APIResponse<MutableList<Courses>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<Courses>>>,
                response: Response<APIResponse<MutableList<Courses>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<MutableList<Courses>>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }
        })
    }

    fun eBookCourses(retrofitCallBack: RetrofitCallBack) {
        val client: HomeContent = APIClient().getInstance().create(HomeContent::class.java)
        val call: Call<APIResponse<MutableList<EbookDataModel>>> = client.getECourses()
        call.enqueue(object : Callback<APIResponse<MutableList<EbookDataModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<EbookDataModel>>>,
                response: Response<APIResponse<MutableList<EbookDataModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<EbookDataModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun banners(retrofitCallBack: RetrofitCallBack) {
        val client: HomeContent = APIClient().getInstance().create(HomeContent::class.java)
        val call: Call<APIResponse<MutableList<HomeAdvBannerModel>>> = client.getBanners()
        call.enqueue(object : Callback<APIResponse<MutableList<HomeAdvBannerModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<HomeAdvBannerModel>>>,
                response: Response<APIResponse<MutableList<HomeAdvBannerModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<HomeAdvBannerModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}