package com.example.gallaryapplication.util

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.util.*

class SharedPreferencesHelper(context: Context) {

    private val LOGIN_TIME = "Login Time"
    private val PERMISSION_STATUS = "Permission_status"
    private val time = 0
    private val permission = false
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveLoginTime(key: Date) {
        prefs.edit().putLong(LOGIN_TIME, key.time).apply()
    }

    fun getLoginTime() = prefs.getLong(LOGIN_TIME, time.toLong())


    fun savePermissionStatus(value: Boolean) {
        prefs.edit {
            putBoolean(PERMISSION_STATUS,value)
        }
    }
    fun getPermissionStatus() = prefs.getBoolean(PERMISSION_STATUS,permission)


}//end of sharedpreferences