package com.cmt.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.cmt.helper.ICallback
import com.cmt.helper.IConstants
import com.cmt.helper.internetActive
import com.cmt.helper.setSnackBar
import com.cmt.internet.ConnectionStateMonitor
import com.google.android.material.snackbar.Snackbar
import com.the_pride_ias.R

open class BaseActivity : AppCompatActivity() {
    private var internetSnackBar: Snackbar? = null
    var permissionStatus: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!internetActive(this)) {
            internetSnackBar?.dismiss()
            internetSnackBar = setRedSnackBar()
        }

        val connectionStateMonitor = ConnectionStateMonitor()
        connectionStateMonitor.enable(this)
        connectionStateMonitor.observer.observe(this) {
            if (it) {
                internetSnackBar?.dismiss()
            } else {
                internetSnackBar?.dismiss()
                internetSnackBar = setRedSnackBar()
            }
        }
    }

    private fun setRedSnackBar(): Snackbar {
        val snackBar = Snackbar
            .make(
                this.findViewById(android.R.id.content),
                "It seems that you are offline. Please check your internet connectivity",
                Snackbar.LENGTH_INDEFINITE
            )
        val view = snackBar.view
//        val textView: TextView =
//            view.findViewById(R.id.snackbar_text)
//        textView.maxLines = 5
//        textView.ellipsize = TextUtils.TruncateAt.END

//        view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
//        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackBar.show()
        return snackBar
    }

    fun checkPermissions(allow: Boolean, callback: ICallback? = null) :Boolean{
        val permissions =
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        if (!hasPermissions(this, *permissions)) {
            if (allow) {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    IConstants.PermissionCode.app_permission
                )
            } else {
                callback?.delegate(false)
                return false
            }
        } else {
            callback?.delegate(true)
            permissionStatus.postValue(true)
            return true
        }
        return false
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == IConstants.PermissionCode.app_permission) {
            if (hasPermissions(this, *permissions)) {
                permissionStatus.postValue(true)
                alertDialog?.dismiss()
            } else {
                permissionStatus.postValue(false)
            }
        }
    }

    private var alertDialog: AlertDialog? = null
    fun openPermissionWarning() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.hint_permission_title))
        builder.setMessage(getString(R.string.hint_please_allow_permission))
            .setPositiveButton(getString(R.string.hint_settings)) { _, _ ->
                alertDialog?.cancel()
                val intent = Intent()
                intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                overridePendingTransition(R.anim.enter, R.anim.exit)

            }
            .setNegativeButton(getString(R.string.hint_cancel)) { _, _ ->
                (currentFocus?.context as Activity).setSnackBar(getString(R.string.hint_permission_sorry))
                finish()
                overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
            }
        alertDialog = builder.create()
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        val button = alertDialog?.window?.findViewById<Button>(android.R.id.button1)
        val button2 = alertDialog?.window?.findViewById<Button>(android.R.id.button2)

        button?.setTextColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_green_dark
            )
        )
        button2?.setTextColor(
            ContextCompat.getColor(
                this,
                android.R.color.holo_red_dark
            )
        )
    }
}