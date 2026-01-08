package com.cmt.services.helper

interface RetrofitCallBack {
    fun responseListener(response: Any? = null, error: String? = null)
}