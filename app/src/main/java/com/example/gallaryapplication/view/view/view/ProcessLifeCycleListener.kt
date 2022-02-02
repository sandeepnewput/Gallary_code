package com.example.gallaryapplication.view.view.view

import android.util.Log

class ProcessLifeCycleListener:AppLifeCycleListener {
    override fun onAppForeground() {
       Log.d("forground","App is in onAppForeground")
    }

    override fun onAppBackground() {
        Log.d("background","App is in onAppBackground")
    }
}