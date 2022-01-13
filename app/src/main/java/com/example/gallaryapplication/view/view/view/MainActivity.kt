package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SessionListener {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var listener: SessionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerSessionListener(this)

    }//onCreate method ends

    override fun onStart() {
        super.onStart()
        listener?.onAppForeground()
        val date2 = mainActivityViewModel.getCurrentDateTime()
        Log.d("date2", "date2 is ${date2.time} in logintime observer ")
        mainActivityViewModel.hasLoginSession(date2.time, mainActivityViewModel.getLoggedintime())

        mainActivityViewModel.isloggedin.observe(this) { isLogin ->
            Log.d("response", "response is $isLogin")
            if (isLogin) {
                val date2 = mainActivityViewModel.getCurrentDateTime()
                Log.d("date2", "date2 is $date2 in isloggedin observer")
                mainActivityViewModel.saveLoggedinTime(date2)
                onSessionIn()
            } else {
                onSessionLogout()
                Log.d("else", "else part running in isloggedin observer")
            }
        }

        Log.d("onStart", "onStart method called")
    }


    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume method callled")
    }//end of onResume method

//    fun getCurrentDateTime(): Date {
//        return Calendar.getInstance().time
//    }


    override fun onPause() {
        Log.d("onPause", "onPause method called")
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        listener?.onAppBackground()
        Log.d("onStop", "onStop method called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy method called")
    }


    fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(R.id.loginFragment)
    }

    fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.photoFragment)
    }

    override fun onAppForeground() {
        Log.d("for", "App is in Foreground")
    }

    override fun onAppBackground() {
        Log.d("back", "App is in Background")
    }

    fun registerSessionListener(listener: SessionListener) {
        this.listener = listener
    }


//
//    override fun onBackPressed() {
//        Log.d("onbackpress","onBackPressed method is called")
//        val fragment =
//            this.supportFragmentManager.findFragmentById(R.id.loginFragment)
//        (fragment as? LogoutListener)?.onBackPressed()?.not()?.let {
////            super.onBackPressed()
//        }
//    }


}//end of main activity






