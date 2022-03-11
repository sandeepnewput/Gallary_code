package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.view.ProcessLifeCycleListener.isForeGround
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeLogin()
    }//onCreate method ends

    init {
        isForeGround.observe(this) {
            if (it) mainActivityViewModel.checkLogin()
        }
    }

    private fun observeLogin() {
        mainActivityViewModel.isLogIn.observe(this, { isLogin ->
            if (isLogin) {
                onSessionIn()
            } else {
                onSessionLogout()
            }
        })
    }

    private fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(R.id.loginFragment)
    }

    private fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.bottomNavFragment)

    }


}//end of main activity








