package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(

    private val prefs: SharedPreferencesHelper
) : ViewModel() {


    private val _localtime by lazy { MutableLiveData<Long>() }
    val localtime: LiveData<Long> = _localtime

    private val _isLogIn by lazy { MutableLiveData<Boolean>() }
    val isLogIn: LiveData<Boolean> = _isLogIn


    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    fun checkLogin(){
        _loading.postValue(true)
        val date2 = currentDate
       hasLoginSession(date2.time,getLoggedintime())
    }


    fun saveLoggedinTime(value: Date) = prefs.saveLoggedinTime(value)

    fun getLoggedintime() = prefs.getLoggedinTime()


    val currentDate get() = Calendar.getInstance().time

    fun getCurrentDateTime() = Calendar.getInstance().time


    private fun hasLoginSession(latestTime: Long, loginTime: Long) = if(loginTime != 0.toLong()) {
        val diff = (latestTime - loginTime) / 60000
        _isLogIn.postValue(diff < 2)

    }else{
        _isLogIn.postValue(false)

    }//end of isLoginSession


}//end of ImageVideoViewModel




