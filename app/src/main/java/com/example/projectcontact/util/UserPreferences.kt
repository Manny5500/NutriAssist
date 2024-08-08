package com.example.projectcontact.util

import android.content.Context
import android.content.SharedPreferences

object UserPreferences {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(context: Context, userId: String) {
        getPreferences(context).edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_ID, null)
    }

    fun clearUserId(context: Context) {
        getPreferences(context).edit().remove(KEY_USER_ID).apply()
    }
}
