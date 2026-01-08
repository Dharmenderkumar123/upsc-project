package com.cmt.helper

import android.content.Context

class AppPreferences {
    fun setUserId(context: Context?, userId: String?) {
        val preferences =
            context?.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
                ?.edit()
        preferences?.putString(IConstants.Pref.user_id, userId)
        preferences?.apply()
    }

    fun getUserId(context: Context): String {
        val preferences =
            context?.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
        return preferences?.getString(IConstants.Pref.user_id, null) ?: ""
    }

    fun setAccessToken(context: Context, access_token: String) {
        val preferences =
            context.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
                .edit()
        preferences.putString(IConstants.Pref.access_token, access_token)
        preferences.apply()
    }

    fun getAccessToken(context: Context): String? {
        val preferences =
            context.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
        return if (preferences.contains(IConstants.Pref.access_token)) {
            preferences.getString(IConstants.Pref.access_token, null)
        } else {
            null
        }
    }

    fun setPushNotificationToken(context: Context, token: String) {
        val preferences =
            context.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
                .edit()
        preferences.putString(IConstants.Pref.push_notification_token, token)
        preferences.apply()
    }

    fun getPushNotificationToken(context: Context): String? {
        val preferences =
            context.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
        return if (preferences.contains(IConstants.Pref.push_notification_token)) {
            preferences.getString(IConstants.Pref.push_notification_token, null)
        } else {
            null
        }
    }

    fun signOut(context: Context) {
        val preferences =
            context.getSharedPreferences(IConstants.Pref.shared_preference, Context.MODE_PRIVATE)
                .edit()
        preferences.clear()
        preferences.apply()
    }

    fun logout(context: Context) {
        val preferences = context.getSharedPreferences(IConstants.Pref.shared_preference,Context.MODE_PRIVATE).edit()
        preferences.remove(IConstants.Pref.user_id)
        preferences.apply()
    }
}