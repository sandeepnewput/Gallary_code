package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.*


object ProcessLifeCycleListener : LifecycleObserver {

    private val isForeground by lazy { MutableLiveData<Boolean>() }
    val isForeGround: LiveData<Boolean> = isForeground

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground() {
        isForeground.postValue(true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        isForeground.postValue(false)
    }
}