package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val prefs: SharedPreferencesHelper
) : ViewModel() {

    private val currentDateTime get() = Calendar.getInstance().time

    private fun saveLogInTime(value: Date) = prefs.saveLoginTime(value)

    private val _isLogin by lazy { MutableLiveData<Boolean>() }
    val isLogin: LiveData<Boolean> = _isLogin

    private val userName: String = "sandeep"
    private val userPassword: String = "1234"

    fun login(username: String, password: String) =
        if (username == userName && password == userPassword) {
            _isLogin.value = true
            saveLogInTime(currentDateTime)
        } else _isLogin.value = false
}//end of LoginViewModel