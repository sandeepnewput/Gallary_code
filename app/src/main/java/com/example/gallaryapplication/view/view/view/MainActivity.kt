package com.example.gallaryapplication.view.view.view



import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val appController = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }//onCreate method ends

    override fun onStart() {
        super.onStart()
        mainActivityViewModel.checkLogin()
        appController?.AppLifeCycleCallback()?.onActivityStarted(this)

        mainActivityViewModel.isloggedin.observe(this) { isLogin ->
            if (isLogin) {
                val date2 = mainActivityViewModel.getCurrentDateTime()
                mainActivityViewModel.saveLoggedinTime(date2)
                onSessionIn()
            } else {
                onSessionLogout()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        appController?.AppLifeCycleCallback()?.onActivityStopped(this)
    }

    private fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(R.id.loginFragment)
    }

    private fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.photoFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}//end of main activity






