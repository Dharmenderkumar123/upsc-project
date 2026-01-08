package com.cmt.services.api

import com.cmt.services.apiInterface.Ebook
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.EbookCourseSubjectModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EbookAPI {
    fun eBookList(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Ebook = APIClient().getInstance().create(Ebook::class.java)
        val call: Call<APIResponse<MutableList<EbookCourseSubjectModel>>> =
            client.ebooksList(params)
        call.enqueue(object : Callback<APIResponse<MutableList<EbookCourseSubjectModel>>> {
            override fun onResponse(
                call: Call<APIResponse<MutableList<EbookCourseSubjectModel>>>,
                response: Response<APIResponse<MutableList<EbookCourseSubjectModel>>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(
                call: Call<APIResponse<MutableList<EbookCourseSubjectModel>>>,
                t: Throwable
            ) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }
}