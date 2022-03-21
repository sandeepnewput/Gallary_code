package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.view.ProcessLifeCycleListener.isForeGround
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bottom_navigation_view.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeLogin()
        checkLogin()
        Log.d("created","main activity is created ")
    }//onCreate method ends


    override fun onDestroy() {
        super.onDestroy()
        Log.d("ondestroy","onDestroy method is called")
    }

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
        supportFragmentManager.popBackStack(R.id.bottomNavFragment,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
       findNavController(R.id.fragment).navigate(R.id.launchingFragment)

    }//end of onSeesionLogut


}//end of main activity








