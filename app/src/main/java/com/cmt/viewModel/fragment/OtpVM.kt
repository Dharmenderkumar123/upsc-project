package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.*
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.apiInterface.Authentication
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.view.GenericTextWatcher
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentOtpBinding

class OtpVM : ViewModel() {
    lateinit var binding: FragmentOtpBinding
    var userId: MutableLiveData<String> = MutableLiveData()

    fun otp1TextChange(): GenericTextWatcher {

        return GenericTextWatcher(
            binding.etOtp1,
            binding.etOtp2, binding.etOtp1
        )
    }

    fun otp2TextChange(): GenericTextWatcher {

        return GenericTextWatcher(
            binding.etOtp2,
            binding.etOtp3, binding.etOtp1
        )
    }

    fun otp3TextChange(): GenericTextWatcher {

        return GenericTextWatcher(
            binding.etOtp3,
            binding.etOtp4, binding.etOtp2
        )
    }

    fun otp4TextChange(): GenericTextWatcher {

        return GenericTextWatcher(
            binding.etOtp4,
            binding.etOtp4, binding.etOtp3
        )
    }

    fun continueButton(view: View) {
        view.hideKeyboard()
        when {
            binding.etOtp1.text.toString().isEmpty() -> {
                (view.context as FragmentActivity).setSnackBar("Please Enter Valid OTP")

            }
            binding.etOtp2.text.toString().isEmpty() -> {
                (view.context as FragmentActivity).setSnackBar("Please Enter Valid OTP")

            }
            binding.etOtp3.text.toString().isEmpty() -> {
                (view.context as FragmentActivity).setSnackBar("Please Enter Valid OTP")

            }
            binding.etOtp4.text.toString().isEmpty() -> {
                (view.context as FragmentActivity).setSnackBar("Please Enter Valid OTP")
            }
            else -> {
                val activity = view.context as FullPlainActivity
                val otp =
                    binding.etOtp1.text.toString() + binding.etOtp2.text.toString() + binding.etOtp3.text.toString() + binding.etOtp4.text.toString()
                val params = getGlobalParams(activity)
                params[IConstants.Params.otp] = otp
                params[IConstants.Params.user_id] = userId.value.toString()
                activity.activityLoader(true)
                AuthenticationAPI().verifyOtp(params, object : RetrofitCallBack {
                    override fun responseListener(response: Any?, error: String?) {
                        activity.activityLoader(false)
                        if (error != null) {
                            activity.setSnackBar(error)
                        } else {
                            val apiResponse = response as? APIResponse<*>
                            if (apiResponse?.error_code == IConstants.Response.valid) {
                                if (AppController.PassWordType == IConstants.Defaults.forgot_password) {
                                    val intent = Intent(activity, MainActivity::class.java)
                                    activity.setSnackBar(apiResponse.message)
                                    activity.startActivity(intent)
                                    activity.finishAffinity()
                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                } else if (AppController.PassWordType == IConstants.Defaults.register) {
                                    AppPreferences().setUserId(activity, userId.value)
                                    val intent = Intent(activity, MainActivity::class.java)
                                    activity.setSnackBar(apiResponse.message)
                                    activity.startActivity(intent)
                                    activity.finishAffinity()
                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                } else if (AppController.PassWordType == IConstants.Defaults.login) {
                                    AppPreferences().setUserId(activity, userId.value)
                                    val intent = Intent(activity, MainActivity::class.java)
                                    Toast.makeText(activity, apiResponse.message, Toast.LENGTH_SHORT).show()
                                    activity.startActivity(intent)
                                    activity.finishAffinity()
                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                }
                            } else {
                                activity.setSnackBar(apiResponse?.message)
                            }
                        }
                    }

                })
            }

        }
    }

    fun resend(view: View) {
        val activity = view.context as? FullPlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        params[IConstants.Params.user_id] = userId.value.toString()
        AuthenticationAPI().resend(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        Toast.makeText(activity, apiResponse.message, Toast.LENGTH_SHORT).show()
                        activity?.setSnackBar(apiResponse.message)
                    } else {
                        activity?.activityLoader(false)
                        activity?.setSnackBar(apiResponse?.message)
                    }
                }
            }

        })

    }

}