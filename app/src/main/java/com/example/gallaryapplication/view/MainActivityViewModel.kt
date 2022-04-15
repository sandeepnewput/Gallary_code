package com.example.gallaryapplication.view


import androidx.lifecycle.*
import com.example.gallaryapplication.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val prefs: SharedPreferencesHelper
) : ViewModel() {

    private val _isLogIn by lazy { MutableLiveData<Boolean>() }
    val isLogIn: LiveData<Boolean> = _isLogIn

    fun checkLogin() {
        hasLoginSession(getCurrentDateTime().time, getLogInTime())
    }
    private fun saveLogInTime(value: Date) = prefs.saveLoginTime(value)

    private fun getLogInTime() = prefs.getLoginTime()

    private fun getCurrentDateTime() = Calendar.getInstance().time

    private fun hasLoginSession(latestTime: Long, loginTime: Long) {
        if (loginTime != 0.toLong()) {
            val diff = (latestTime - loginTime) / 60000
            _isLogIn.value = diff < 1
            if (diff < 1) {
                saveLogInTime(getCurrentDateTime())
            }
        } else {
            _isLogIn.value = false
        }
    }

}//end of ImageVideoViewModel








