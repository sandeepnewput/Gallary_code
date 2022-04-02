package com.example.gallaryapplication.view.view.util

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*

class SharedPreferencesHelper(context: Context) {

    private val LOGIN_TIME = "Login Time"
    private val time = 0
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveLoginTime(key: Date) {
        prefs.edit().putLong(LOGIN_TIME, key.time).apply()
    }

    fun getLoginTime() = prefs.getLong(LOGIN_TIME, time.toLong())


}//end of sharedpreferences