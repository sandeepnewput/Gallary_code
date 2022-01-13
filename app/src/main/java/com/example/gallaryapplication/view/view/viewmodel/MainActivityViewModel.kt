package com.example.gallaryapplication.view.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject



//class ImageVideoViewModel(application:Application):AndroidViewModel(application) {
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    private val prefs: SharedPreferencesHelper
) : AndroidViewModel(application) {


    private val _localtime by lazy { MutableLiveData<Long>() }
    val localtime: LiveData<Long> = _localtime

    private val _isloggedin by lazy { MutableLiveData<Boolean>() }
    val isloggedin: LiveData<Boolean> = _isloggedin


//    private val prefs =  SharedPreferencesHelper(getApplication())

    fun saveLoggedinTime(value: Date) {
        prefs.saveLoggedinTime(value)
    }

    fun getLoggedintime() = prefs.getLoggedinTime()


    val currentDate get() = Calendar.getInstance().time

    fun getCurrentDateTime() = Calendar.getInstance().time


    fun hasLoginSession(latestTime: Long, loginTime: Long) {
        if(loginTime != 0.toLong()) {
            val diff = (latestTime - loginTime) / 60000
            Log.d("diff", "diff is $diff")
            Log.d("withContext", "in isLoginSession function")
            _isloggedin.value = diff < 2
        }else{
            _isloggedin.value = false
        }

    }//end of isLoginSession


}//end of ImageVideoViewModel




