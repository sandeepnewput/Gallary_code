package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.*


object ProcessLifeCycleListener : LifecycleObserver {


    private val _isForeground by lazy { MutableLiveData<Boolean>() }
    val isForeGround: LiveData<Boolean> = _isForeground

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground() {
        _isForeground.postValue(true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        _isForeground.postValue(false)
    }

}