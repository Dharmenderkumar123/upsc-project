package com.cmt.viewModel.fragment

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.cmt.helper.*
import com.cmt.my_doctors.app.AppController
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.RegisterResponseModel
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.R

class ForgotPasswordVM : ViewModel() {

    var userId: MutableLiveData<String> = MutableLiveData()
    var mobileNumber: MutableLiveData<String> = MutableLiveData()

    fun sendOtp(view: View) {
        view.hideKeyboard()
        val activity = view.context as FullPlainActivity
        if (mobileNumber.value?.trim().isNullOrEmpty()) {
            activity.setSnackBar("Please enter mobile number/Email")
        } else {
            val params = getGlobalParams(view.context)
            mobileNumber.value?.let {
                params[IConstants.Params.mobile] = it
            }
            activity.activityLoader(true)
            AuthenticationAPI().forgotPassword(params, object : RetrofitCallBack {
                override fun responseListener(response: Any?, error: String?) {
                    activity.activityLoader(false)
                    if (error != null) {
                        activity.setSnackBar(error)
                    } else {
                        val apiResponse = response as? APIResponse<*>
                        if (apiResponse?.error_code == IConstants.Response.valid) {
                            val builder = AlertDialog.Builder(activity, R.style.alertDialog)
                            builder.setMessage("Password sent to your registered mobile number")
                            builder.setIcon(android.R.drawable.ic_dialog_alert)
                            builder.setPositiveButton("Ok") { dialogInterface, which ->
                                val userModel = apiResponse.data as? RegisterResponseModel
                                Toast.makeText(activity, apiResponse.message, Toast.LENGTH_SHORT)
                                    .show()
                                AppController.PassWordType = IConstants.Defaults.forgot_password
                                val intent = Intent(activity, FullPlainActivity::class.java)
                                intent.putExtra(
                                    IConstants.IntentStrings.type,
                                    IConstants.FragmentType.Login
                                )
                                intent.putExtra(
                                    IConstants.IntentStrings.payload,
                                    userId.value ?: ""
                                )
                                activity.startActivity(intent)
                                dialogInterface.dismiss()
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        } else {
                            activity.setSnackBar(apiResponse?.message)
                        }
                    }

                }

            })
        }


    }
}