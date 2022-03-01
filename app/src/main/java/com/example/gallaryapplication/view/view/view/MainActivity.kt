package com.example.gallaryapplication.view.view.view



import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {





    private val viewModel: MainActivityViewModel by viewModels()
    private val appController = AppController()

////    private lateinit var binding: ActivityMainBinding
//
//    private var _binding: ActivityMainBinding? = null
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

    }//onCreate method ends

    override fun onStart() {
        super.onStart()
        viewModel.checkLogin()
        appController?.AppLifeCycleCallback()?.onActivityStarted(this)



        viewModel.isLogIn.observe(this) { isLogin ->
            if (isLogin) {
                val date2 = viewModel.getCurrentDateTime()
                viewModel.saveLoggedinTime(date2)
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
        findNavController(R.id.fragment).navigate(R.id.bottomNavigationViewFragment)

    }


}//end of main activity






