package com.cmt.services.apiInterface

import com.cmt.services.model.APIResponse
import com.cmt.services.model.VimeoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.GET


interface ViemoInterface {
    @GET("{video_id}/config")
    fun getVimeoUrlResponse(@FieldMap userParams: HashMap<String, String>): Call<ResponseBody>
}