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
class MainActivity :  AppCompatActivity() {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val appController = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }//onCreate method ends

    override fun onStart() {
        super.onStart()
       mainActivityViewModel.checkLogin()
        appController?.AppLifeCycleCallback()?.onActivityStarted(this)
        Log.d("onStart", "onStart method called $appController")
    }


    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume method callled")
    }//end of onResume method




    override fun onPause() {
        Log.d("onPause", "onPause method called")
        super.onPause()
    }

    override fun onStop() {
        super.onStop()

        appController?.AppLifeCycleCallback()?.onActivityStopped(this)
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

}//end of main activity






