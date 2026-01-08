package com.cmt.viewModel.fragment

import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.*
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PasswordDataModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentSettingsBinding

class SettingsVM : ViewModel() {
    lateinit var binding: FragmentSettingsBinding
    var passwordData: MutableLiveData<PasswordDataModel> = MutableLiveData()

    init {
        passwordData.value = PasswordDataModel()
    }

    var toggle1: Boolean = true
    var toggle2: Boolean = true
    var toggle3: Boolean = true


    fun visiblePassword1() {
        if (toggle1) {
            binding.oldPassword.transformationMethod = null
            binding.oldPassword.setSelection(binding.oldPassword.text?.length ?: 0)
            binding.eye1.setImageResource(R.drawable.eye_1)
            binding.eye1.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
            toggle1 = false
        } else {
            binding.oldPassword.transformationMethod = PasswordTransformationMethod()
            binding.oldPassword.setSelection(binding.oldPassword.text?.length ?: 0)
            binding.eye1.setImageResource(R.drawable.eye)
            binding.eye1.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    android.R.color.darker_gray
                )
            )
            toggle1 = true
        }
    }

    fun visiblePassword2() {
        if (toggle2) {
            binding.newPassword.transformationMethod = null
            binding.newPassword.setSelection(binding.newPassword.text?.length ?: 0)
            binding.eye2.setImageResource(R.drawable.eye_1)
            binding.eye2.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )

            toggle2 = false
        } else {
            binding.newPassword.transformationMethod = PasswordTransformationMethod()
            binding.newPassword.setSelection(binding.newPassword.text?.length ?: 0)
            binding.eye2.setImageResource(R.drawable.eye)
            binding.eye2.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    android.R.color.darker_gray
                )
            )
            toggle2 = true
        }
    }

    fun visiblePassword3() {
        if (toggle3) {
            binding.reTypePassword.transformationMethod = null
            binding.reTypePassword.setSelection(binding.reTypePassword.text?.length ?: 0)
            binding.eye3.setImageResource(R.drawable.eye_1)
            binding.eye3.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorPrimary
                )
            )
            toggle3 = false
        } else {
            binding.reTypePassword.transformationMethod = PasswordTransformationMethod()
            binding.reTypePassword.setSelection(binding.reTypePassword.text?.length ?: 0)
            binding.eye3.setImageResource(R.drawable.eye)
            binding.eye3.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    android.R.color.darker_gray
                )
            )
            toggle3 = true
        }
    }

    fun updatePassword(view: View) {
        view.hideKeyboard()
        val activity = view.context as PlainActivity
        when {
            passwordData.value?.current_password?.trim().isNullOrEmpty() -> {
                activity.setSnackBar(view.context.getString(R.string.txt_enter_old_password))
            }
            passwordData.value?.new_password.isNullOrEmpty() -> {
                activity.setSnackBar(view.context.getString(R.string.txt_enter_password))
            }
            passwordData.value?.re_enter_password.isNullOrEmpty() -> {
                activity.setSnackBar(view.context.getString(R.string.txt_re_enter_password))
            }
            passwordData.value?.new_password?.length ?: 0 < 6 -> {
                activity.setSnackBar(view.context.getString(R.string.txt_enter_valid_password))

            }

            passwordData.value?.new_password != passwordData.value?.re_enter_password -> {
                activity.setSnackBar(
                    view.context.getString(R.string.txt_password_mismatch)
                )
            }

            else -> {
                val params = getGlobalParams(activity)
                params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
                passwordData.value?.new_password?.let {
                    params[IConstants.Params.new_password] = it
                }
                passwordData.value?.current_password?.let {
                    params[IConstants.Params.old_password] = it
                }
                activity.activityLoader(true)
                AuthenticationAPI().changePassword(params, object : RetrofitCallBack {
                    override fun responseListener(response: Any?, error: String?) {
                        if (error != null) {
                            activity.setSnackBar(error)
                        } else {
                            val apiResponse = response as? APIResponse<*>
                            if (apiResponse?.error_code == IConstants.Response.valid) {
                                activity.activityLoader(false)
                                Toast.makeText(
                                    activity,
                                    "Password Updated Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(activity, FullPlainActivity::class.java)
                                intent.putExtra(
                                    IConstants.IntentStrings.type,
                                    IConstants.FragmentType.Login
                                )
                                activity.startActivity(intent)
                            } else {
                                activity.activityLoader(false)
                                activity.setSnackBar(apiResponse?.message)
                            }
                        }
                    }

                })

            }

        }
    }
}