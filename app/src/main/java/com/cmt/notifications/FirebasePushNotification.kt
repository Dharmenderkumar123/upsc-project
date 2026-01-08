package com.cmt.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cmt.helper.AppPreferences
import com.cmt.view.activity.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.the_pride_ias.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class FirebasePushNotification : FirebaseMessagingService() {
    private val classTAG = "FIREBASE"
    override fun onNewToken(p0: String) {
        AppPreferences().setPushNotificationToken(applicationContext, p0)
        Log.d(classTAG, "onNewToken: $p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(classTAG, "onMessageReceived: $p0")
        if (p0.notification != null) {
            p0.notification?.let {
                showNotification(it.title, it.body)
            }
        }
        if (p0.data.isNotEmpty()) {
            val title = p0.data["title"] ?: "New Notification"
            val message = p0.data["body"]
                ?: "Welcome to ${applicationContext.getString(R.string.app_name)}, Start using it"
            val image = p0.data["image"] ?: ""

            showNotification(title, message, image)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(title: String?, message: String?, image: String? = null) {
        val soundUri =
            Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notification_sound)
        Log.d(classTAG, soundUri.toString())

        val i = Intent(this, SplashActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT)
        val channelID = getString(R.string.default_notification_channel_id)

        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.pride_ias_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pi)
            .setColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)


        val manager = NotificationManagerCompat.from(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelID, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.enableLights(true)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.setSound(soundUri, audioAttributes)
            manager.createNotificationChannel(channel)
        } else {
            notification.setSound(soundUri);
        }
        manager.notify(createID(), notification.build())
    }

    private fun createID(): Int {
        val now = Date()
        return SimpleDateFormat("ddHHmmss", Locale.getDefault()).format(now).toInt()
    }
}