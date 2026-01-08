package com.cmt.viewModel.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.AuthenticationAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.SplashActivity
import androidx.core.content.ContextCompat.startActivity

import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.cmt.internet.ConnectionStateMonitor
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.BuildConfig
import com.the_pride_ias.R


class SplashVm(application: Application) : BaseViewModel(application) {
    var noInternet: MutableLiveData<Boolean> = MutableLiveData()

    init {
        noInternet.value = false
    }

    fun setZoomForImage(view: ImageView) {
        val sX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.1f, 1f)
        val sY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.1f, 1f)
        val zoomAnimatorSet = AnimatorSet()
        zoomAnimatorSet.playTogether(sX, sY)
        zoomAnimatorSet.interpolator = LinearInterpolator()
        zoomAnimatorSet.duration = 1200
        zoomAnimatorSet.setTarget(view)
        zoomAnimatorSet.start()
        Handler(Looper.getMainLooper()).postDelayed({
//            checkVersion(BuildConfig.VERSION_CODE, view)
            if (!AppPreferences().getUserId(view.context).isEmpty()) {
                val intent = Intent(view.context, MainActivity::class.java)
                (view.context as SplashActivity).startActivity(intent)
                (view.context as SplashActivity).overridePendingTransition(
                    R.anim.enter,
                    R.anim.exit
                )
                (view.context as SplashActivity).finishAffinity()
            } else {
                val intent = Intent(
                    view.context as SplashActivity,
                    FullPlainActivity::class.java)
                intent.putExtra(
                    IConstants.IntentStrings.type,
                    IConstants.FragmentType.AppGuidFragment
                )
                (view.context as SplashActivity).startActivity(intent)
            }
        }, 2000)
    }

    private fun checkVersion(versionCode: Int, view: ImageView) {
        val params = getGlobalParams(view.context as SplashActivity)
        params[IConstants.Params.current_version] = versionCode.toString()
        AuthenticationAPI().versionCheck(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (error != null) {
                    if (noInternet.value == true) {

                    } else {
                        //(view.context as SplashActivity).setSnackBar(error)
                    }
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        if (!AppPreferences().getUserId(view.context).isEmpty()) {
                            val intent = Intent(view.context, MainActivity::class.java)
                            (view.context as SplashActivity).startActivity(intent)
                            (view.context as SplashActivity).overridePendingTransition(
                                R.anim.enter,
                                R.anim.exit
                            )
                            (view.context as SplashActivity).finishAffinity()
                        } else {
                            val intent = Intent(
                                view.context as SplashActivity,
                                FullPlainActivity::class.java
                            )
                            intent.putExtra(
                                IConstants.IntentStrings.type,
                                IConstants.FragmentType.AppGuidFragment
                            )
                            (view.context as SplashActivity).startActivity(intent)
                        }
                    } else {
                        //(view.context as SplashActivity).setSnackBar(apiResponse?.message)
                        openAlertDialog(view.context as SplashActivity)
                    }
                }
            }

        })

    }

    private fun openAlertDialog(context: SplashActivity) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setIcon(R.drawable.pride_ias_logo)
            .setTitle("Update")
            .setMessage("There is new version available in Play Store. Please update to continue using application")
            .setNegativeButton("Cancel") { dialogInterface, i -> context.finishAffinity() }
            .setPositiveButton("ok") { dialogInterface, i ->
                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                    )
                    context.startActivity(intent)
                    context.finishAffinity()
                    context.overridePendingTransition(R.anim.enter, R.anim.exit)
                } catch (e: ActivityNotFoundException) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                    )
                    context.startActivity(intent)
                    context.finishAffinity()
                    context.overridePendingTransition(R.anim.enter, R.anim.exit)
                }
            }
            .show()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }


}