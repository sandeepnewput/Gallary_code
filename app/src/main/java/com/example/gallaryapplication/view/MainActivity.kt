package com.example.gallaryapplication.view


import android.content.Context
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions.*
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.ProcessLifeCycleListener.isForeGround
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeLogin()
        checkLogin()

    }//onCreate method ends


    private fun checkLogin() {
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

    private fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.bottomNavFragment)
    }

    private fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(
            R.id.loginFragment,
            null,
            navOptions {
                Builder().setPopUpTo(R.id.bottomNavFragment, true)
            }
        )


    }//end of onSeesionLogut

}//end of main activity








