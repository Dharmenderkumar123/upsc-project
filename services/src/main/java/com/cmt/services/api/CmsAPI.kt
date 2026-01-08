package com.cmt.services.api

import com.cmt.services.apiInterface.CMS
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CmsAPI {
    fun aboutUs(retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<CMSModel>> = client.getAbout()
        call.enqueue(object : Callback<APIResponse<CMSModel>> {
            override fun onResponse(
                call: Call<APIResponse<CMSModel>>,
                response: Response<APIResponse<CMSModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<CMSModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun terms(retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<CMSModel>> = client.getTerms()
        call.enqueue(object : Callback<APIResponse<CMSModel>> {
            override fun onResponse(
                call: Call<APIResponse<CMSModel>>,
                response: Response<APIResponse<CMSModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<CMSModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun faq(retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<MutableList<FaqModel>>> = client.getFaq()
        call.enqueue(object : Callback<APIResponse<MutableList<FaqModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<FaqModel>>>,
                response: Response<APIResponse<MutableList<FaqModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<MutableList<FaqModel>>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun support(retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<SupportModel>> = client.getSupport()
        call.enqueue(object : Callback<APIResponse<SupportModel>> {
            override fun onResponse(
                call: Call<APIResponse<SupportModel>>,
                response: Response<APIResponse<SupportModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<SupportModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun contact(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<Any>> = client.contact_us(params)
        call.enqueue(object : Callback<APIResponse<Any>> {
            override fun onResponse(
                call: Call<APIResponse<Any>>,
                response: Response<APIResponse<Any>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<Any>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    fun notifications(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: CMS = APIClient().getInstance().create(CMS::class.java)
        val call: Call<APIResponse<MutableList<NotificationDataModel>>> =
            client.notifications(params)
        call.enqueue(object : Callback<APIResponse<MutableList<NotificationDataModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<NotificationDataModel>>>,
                response: Response<APIResponse<MutableList<NotificationDataModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<NotificationDataModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}