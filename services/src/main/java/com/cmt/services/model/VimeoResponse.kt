package com.cmt.services.model

import com.google.gson.annotations.SerializedName
import okhttp3.Request

class VimeoResponse {
    @SerializedName("request")
    private lateinit var request: Request


    fun setRequest(request: Request?) {
        if (request != null) {
            this.request = request
        }
    }

    fun getRequest(): Request? {
        return request
    }


}