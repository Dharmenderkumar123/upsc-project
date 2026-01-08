package com.cmt.viewModel.fragment

import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.*
import com.cmt.model.RegistrationModel
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.RegisterResponseModel
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentRegisterBinding

class RegisterVM : ViewModel() {
    lateinit var binding: FragmentRegisterBinding
    var toggle1: Boolean = true
    var registrationData: MutableLiveData<RegistrationModel> = MutableLiveData()

    fun login(view: View) {
        view.hideKeyboard()
        (view.context as FragmentActivity).onBackPressed()
    }

    init {
        registrationData.value = RegistrationModel()
    }

    fun continueRegistration(view: View) {
        view.hideKeyboard()
        AppController.PassWordType = IConstants.Defaults.register
        val activity = view.context as FullPlainActivity
        when {
            registrationData.value?.name?.trim().isNullOrEmpty() -> {
                activity.setSnackBar("Please Enter Your Name")
            }
            registrationData.value?.mobile?.trim().isNullOrEmpty() -> {
                activity.setSnackBar("Please Enter Phone Number")
            }

            registrationData.value?.mobile?.length!! < 10 -> {
                activity.setSnackBar("Please Enter valid Phone Number")
            }

            registrationData.value?.email?.trim().isNullOrEmpty() -> {
                activity.setSnackBar("Please Enter Email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(registrationData.value?.email?.trim() ?: "")
                .matches() -> {
                activity.setSnackBar("Please Enter a Valid Email")
            }
            registrationData.value?.password.isNullOrEmpty() -> {
                activity.setSnackBar("Please Enter Password")
            }
            registrationData.value?.password?.length!! < 6 -> {
                activity.setSnackBar("Password length must be 6 characters")
            }

            else -> {
                val params = getGlobalParams(view.context)
                registrationData.value?.name?.let {
                    params[IConstants.Params.name] = it
                }
                registrationData.value?.password?.let {
                    params[IConstants.Params.password] = it
                }
                registrationData.value?.mobile?.let {
                    params[IConstants.Params.phone_number] = it
                }
                registrationData.value?.email?.let {
                    params[IConstants.Params.email] = it
                }
                AppPreferences().getPushNotificationToken(view.context).toString().let {
                    params[IConstants.Params.device_id] = it
                }

                activity.activityLoader(true)
                AuthenticationAPI().registerAPI(params, object : RetrofitCallBack {
                    override fun responseListener(response: Any?, error: String?) {
                        activity.activityLoader(false)
                        if (error != null) {
                            activity.setSnackBar(error)
                        } else {
                            val apiResponse = response as? APIResponse<*>
                            if (apiResponse?.error_code == IConstants.Response.valid) {
                                val userModel = apiResponse.data as? RegisterResponseModel
                                AppController.PassWordType = IConstants.Defaults.register
                                //AppPreferences().setUserId(activity, userModel?.user_id)
                                val intent = Intent(activity, FullPlainActivity::class.java)
                                intent.putExtra(
                                    IConstants.IntentStrings.type,
                                    IConstants.FragmentType.Otp
                                )
                                intent.putExtra(IConstants.IntentStrings.payload,userModel?.user_id)
                                activity.startActivity(intent)
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit)
                                activity.setSnackBar(apiResponse.message)
                            } else {
                                activity.setSnackBar(apiResponse?.message)
                            }
                        }

                    }

                })

            }
        }
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