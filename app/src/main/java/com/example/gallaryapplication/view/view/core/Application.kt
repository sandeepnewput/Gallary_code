package com.example.gallaryapplication.view.view.core

import android.app.Application
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.gallaryapplication.view.view.view.LaunchingFragment
import com.example.gallaryapplication.view.view.view.MainActivity
import com.example.gallaryapplication.view.view.view.MainActivityViewModel
import com.example.gallaryapplication.view.view.view.ProcessLifeCycleListener
import dagger.hilt.android.HiltAndroidApp

//ProcessLifecycleOwner is a Class that provides lifecycle for the whole application process.
@HiltAndroidApp
class Application: Application() {

    override fun onCreate() {
        super.onCreate()
       ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessLifeCycleListener)
        Log.d("hiltapp","application created")
    }

}