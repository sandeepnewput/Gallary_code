package com.example.gallaryapplication.core

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.gallaryapplication.view.ProcessLifeCycleListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {

    override fun onCreate() {
        super.onCreate()
       ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessLifeCycleListener)

    }

}