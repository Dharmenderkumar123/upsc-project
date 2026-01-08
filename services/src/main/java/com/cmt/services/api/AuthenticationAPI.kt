package com.cmt.services.api

import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.APIClient
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.RegisterResponseModel
import com.cmt.services.model.UserDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationAPI {
    /**
    This is Login API
    Passing @param 'mobile/email'
    'password'
    'deviceId'
     */

    fun loginAPI(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication = APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<UserDetailsModel>> = client.login(params)
        call.enqueue(object : Callback<APIResponse<UserDetailsModel>> {
            override fun onResponse(
                call: Call<APIResponse<UserDetailsModel>>,
                response: Response<APIResponse<UserDetailsModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<UserDetailsModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }


    /**
    This is Register API
    Passing @param 'mobile'
    'email'
    'password'
    'name'
    'device_id'
     */

    fun registerAPI(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<RegisterResponseModel>> = client.register(params)
        call.enqueue(object : Callback<APIResponse<RegisterResponseModel>> {
            override fun onResponse(
                call: Call<APIResponse<RegisterResponseModel>>,
                response: Response<APIResponse<RegisterResponseModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<RegisterResponseModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

    /**
    This is VerifyOTP API
    Passing @param 'user_id'
    'OTP'
     */
    fun verifyOtp(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = client.verify_otp(params)
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

    /**
    This is Change Password API
    Passing @param 'user_id'
    'old_password'
    'password'
    'user_id'
     */
    fun changePassword(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = client.change_password(params)
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

    fun versionCheck(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = client.version_check(params)
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

    fun resend(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<Any>> = client.resendOtp(params)
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

    fun forgotPassword(params: HashMap<String, String>, retrofitCallBack: RetrofitCallBack) {
        val client: Authentication =
            APIClient().getInstance().create(Authentication::class.java)
        val call: Call<APIResponse<RegisterResponseModel>> = client.forgotPassword(params)
        call.enqueue(object : Callback<APIResponse<RegisterResponseModel>> {
            override fun onResponse(
                call: Call<APIResponse<RegisterResponseModel>>,
                response: Response<APIResponse<RegisterResponseModel>>
            ) {
                retrofitCallBack.responseListener(response.body())
            }

            override fun onFailure(call: Call<APIResponse<RegisterResponseModel>>, t: Throwable) {
                val error = t.message ?: "Not found"
                retrofitCallBack.responseListener(response = null, error = error)
            }

        })
    }

}