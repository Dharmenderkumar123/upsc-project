package com.cmt.services.helper

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class BasicAuthInterceptor(userName: String, password: String) : Interceptor {
    private var credentials: String = Credentials.basic(userName, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }

}