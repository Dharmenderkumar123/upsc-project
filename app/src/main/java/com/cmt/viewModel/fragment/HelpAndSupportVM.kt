package com.cmt.viewModel.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.CmsAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SupportModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R


class HelpAndSupportVM : ViewModel() {
    val data: MutableLiveData<SupportModel> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()
    var u: Uri? = null
    var url: String? = null

    fun support(view: View) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        CmsAPI().support(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? SupportModel
                        data.value = dataResponse
                        u = Uri.parse("tel:${data.value?.mobile_1}")
                        url = "https://api.whatsapp.com/send?phone=${data.value?.mobile_2}"
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }

    fun call(view: View) {
        val activity = view.context as? PlainActivity
        val intent = Intent(Intent.ACTION_DIAL, u)
        activity?.startActivity(intent)
    }

    fun whatsApp(view: View) {
        val activity = view.context as? PlainActivity
        val pm: PackageManager = activity!!.packageManager
        //val isInstalled = isPackageInstalled("com.whatsapp", pm)
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            activity.startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /*if (isInstalled) {

        } else {
            Toast.makeText(
                activity, "App is not currently installed on your phone", Toast.LENGTH_SHORT
            ).show()
        }
*/

    }

    fun isPackageInstalled(packageName: String?, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName!!, 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    fun help(view: View) {
        val activity = view.context as? PlainActivity
        val userId: String = AppPreferences().getUserId(view.context)
        if (message.value?.trim().isNullOrEmpty()) {
            activity?.setSnackBar("Please write message")
        } else {
            activity?.activityLoader(true)
            val params = getGlobalParams(view.context)
            message.value?.let {
                params[IConstants.Params.message] = it
            }
            userId.let {
                params[IConstants.Params.user_id] = it
            }
            CmsAPI().contact(params, object : RetrofitCallBack {
                override fun responseListener(response: Any?, error: String?) {
                    activity?.activityLoader(false)
                    if (error != null) {
                        activity?.setSnackBar(error)
                    } else {
                        val apiResponse = response as? APIResponse<*>
                        if (apiResponse?.error_code == IConstants.Response.valid) {
                            Toast.makeText(activity, apiResponse.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(activity, MainActivity::class.java)
                            activity?.startActivity(intent)
                            activity?.finishAffinity()
                            activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
                        } else {
                            activity?.setSnackBar(apiResponse?.message)
                        }
                    }
                }

            })

        }
    }
}