package com.example.gallaryapplication.view.view.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val prefs: SharedPreferencesHelper
):ViewModel() {

    val currentDateTime get()  = Calendar.getInstance().time


    fun saveLoggedinTime(value: Date) = prefs.saveLoggedinTime(value)

}//end of LoginViewModel