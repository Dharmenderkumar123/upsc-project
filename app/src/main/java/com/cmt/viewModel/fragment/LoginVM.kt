package com.cmt.viewModel.fragment

import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.*
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentLoginBinding

class LoginVM : ViewModel() {
    lateinit var binding: FragmentLoginBinding
    var toggle1: Boolean = true
    var userId: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    fun login(view: View) {
        view.hideKeyboard()
        val activity = view.context as FullPlainActivity
        if (userId.value?.trim().isNullOrEmpty()) {
            activity.setSnackBar("Please enter mobile number/Email")
        } else if (password.value.isNullOrEmpty()) {
            activity.setSnackBar("Please enter password")
        } else {
            val params = getGlobalParams(view.context)
            userId.value?.let {
                params[IConstants.Params.mobile] = it
            }
            password.value?.let {
                params[IConstants.Params.password] = it
            }
            AppPreferences().getPushNotificationToken(view.context).toString().let {
                params[IConstants.Params.device_id] = it
            }
            activity.activityLoader(true)
            AuthenticationAPI().loginAPI(params, object : RetrofitCallBack {
                override fun responseListener(response: Any?, error: String?) {
                    activity.activityLoader(false)
                    if (error != null) {
                        activity.setSnackBar(error)
                    } else {
                        val apiResponse = response as? APIResponse<*>
                        if (apiResponse?.error_code == IConstants.Response.valid) {
                            val response = apiResponse.data as? UserDetailsModel
                            if (response?.status.equals("0")) {
                                AppController.PassWordType = IConstants.Defaults.login
                                //AppPreferences().setUserId(activity, response?.user_id)
                                val intent = Intent(activity, FullPlainActivity::class.java)
                                intent.putExtra(
                                    IConstants.IntentStrings.type,
                                    IConstants.FragmentType.Otp
                                )
                                intent.putExtra(IConstants.IntentStrings.payload, response?.user_id)
                                Toast.makeText(activity, "Please verify OTP", Toast.LENGTH_SHORT)
                                    .show()
                                activity.startActivity(intent)
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                binding.ietMail.text = null
                                binding.password.text = null
                            } else {
                                val intent = Intent(activity, MainActivity::class.java)
                                AppPreferences().setUserId(activity, response?.user_id)
                                Toast.makeText(activity, "Successfully Login", Toast.LENGTH_SHORT)
                                    .show()
                                activity.startActivity(intent)
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                activity.finishAffinity()
                            }
                        } else {
                            activity.setSnackBar(apiResponse?.message)
                        }
                    }
                }
            })

        }

    }

    fun register(view: View) {
        view.hideKeyboard()
        val activity = view.context as FullPlainActivity
        val intent = Intent(activity, FullPlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.Register)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)

        binding.ietMail.text = null
        binding.password.text = null
    }

    fun forgotPassword(view: View) {
        view.hideKeyboard()
        val activity = view.context as FullPlainActivity
        val intent = Intent(activity, FullPlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.ForgotPassword)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
        binding.ietMail.text = null
        binding.password.text = null
    }


    fun visiblePassword() {
        if (toggle1) {
            binding.password.transformationMethod = null
            binding.password.setSelection(binding.password.text?.length ?: 0)
            binding.eye.setImageResource(R.drawable.eye_1)
            binding.eye.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
            toggle1 = false
        } else {
            binding.password.transformationMethod = PasswordTransformationMethod()
            binding.password.setSelection(binding.password.text?.length ?: 0)
            binding.eye.setImageResource(R.drawable.eye)
            binding.eye.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    android.R.color.darker_gray
                )
            )
            toggle1 = true
        }
    }
}