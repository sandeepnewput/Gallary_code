package com.example.gallaryapplication.view.view.util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import java.util.*

class SharedPreferencesHelper(context:Context) {

    private val LOGIN_TIME = "Login Time"
    private val FLAG       = "Flag Value"
    private val flagvalue = 0
    private val time = 0.0
    private val LOGIN_HOUR = "Login Hour"
    private val LOGIN_MINUTE = "Login Minute"
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

//    fun saveLoggedinTime(key:String?){
//        prefs.edit().putString(LOGIN_TIME,key).apply()
//    }
fun saveLoggedinTime(key: Date){
        prefs.edit().putLong(LOGIN_TIME, key.time).apply()
}
    fun getLoggedinTime() = prefs.getLong(LOGIN_TIME, time.toLong())

    fun updateLoggedinTime(key: Long){
        prefs.edit().putLong(LOGIN_TIME, 0).apply()
    }

    fun getLoginFlag() = prefs.getInt(FLAG, flagvalue)

    fun updateLoginFlag(flagvalue: Int?){
        Log.d("inside","inside saveloginflag")
        if (flagvalue != null) {

            prefs.edit().putInt(FLAG, flagvalue).apply()
        }
    }




}