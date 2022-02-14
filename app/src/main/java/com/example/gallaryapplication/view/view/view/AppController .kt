package com.example.gallaryapplication.view.view.view

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


class AppController  : Application() {

    private val processLifeCycleListener by lazy { ProcessLifeCycleListener() }
    private var numberOfActivities = 0
    override fun onCreate() {
        super.onCreate()
        Log.d("insideapp","inside app controller")
        registerActivityLifecycleCallbacks(AppLifeCycleCallback())
    }

    inner class AppLifeCycleCallback : ActivityLifecycleCallbacks {

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {

            Log.d("numactivitystart","number of activity $numberOfActivities")

            if (numberOfActivities == 0) {
                processLifeCycleListener?.onAppForeground()
            }
            numberOfActivities += 1
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {

            Log.d("numactivity","number of activity $numberOfActivities")
            if (numberOfActivities == 1) {
                processLifeCycleListener?.onAppBackground()
            }
            numberOfActivities--
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityResumed(activity: Activity) {
        }
    }//end of inner class
}//end of AppController