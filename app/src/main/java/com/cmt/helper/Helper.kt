package com.cmt.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import android.graphics.Bitmap

import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import androidx.customview.R
import com.the_pride_ias.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.HashMap


fun internetActive(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networks: Array<Network> = cm.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val nc = cm.getNetworkCapabilities(network)
            if (nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) hasInternet =
                true
        }
    }
    return hasInternet
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun getGlobalParams(context: Context): HashMap<String, String> {
    val params = HashMap<String, String>()
    params[IConstants.Params.version] = BuildConfig.VERSION_CODE.toString()
    params[IConstants.Params.device_type] = IConstants.Defaults.request_from

    return params
}

@SuppressLint("ResourceAsColor")
fun Activity.setSnackBar(message: String?) {
    if (message != null) {
        val snackBar =
            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val view = snackBar.view
//        val textView: TextView = view.findViewById(R.id.snackbar_text)
//        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
//        textView.maxLines = 5
//        textView.ellipsize = TextUtils.TruncateAt.END
        snackBar.show()
    }
}

fun encodeImage(path: String): String? {
    val imagefile = File(path)
    var fis: FileInputStream? = null
    try {
        fis = FileInputStream(imagefile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    val bm = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    //Base64.de
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun hasPermissions(
    context: Context?,
    vararg permissions: String
): Boolean {
    for (permission in permissions) {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}

/**
 * Used to creating clickable text link in a string for TextView
 */
fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun getMimeType(url: String): String? {
    val type: String?
    val extension: String = removeSpaces(url).let { getExtension(it)!! }
    type =
        MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(extension.lowercase(Locale.getDefault()))
    return type
}

private fun getExtension(url: String): String? {
    return MimeTypeMap.getFileExtensionFromUrl(url)
}

private fun removeSpaces(data: String): String {
    return data.replace("\\s".toRegex(), "")
}
