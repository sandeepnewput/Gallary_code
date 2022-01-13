package com.example.gallaryapplication.view.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val prefs: SharedPreferencesHelper
):AndroidViewModel(application) {

    fun getCurrentDateTime() = Calendar.getInstance().time


    fun saveLoggedinTime(value: Date) {
        prefs.saveLoggedinTime(value)
    }

}//end of LoginViewModel