package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


//    private val viewModel by lazy { ViewModelProvider(this).get(ImageVideoViewModel::class.java) }
     private val viewModel: ImageVideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }//onCreate method ends

    override fun onResume() {
        super.onResume()
        viewModel.getLoggedintime()
        viewModel.localtime.observe(this, androidx.lifecycle.Observer { logintime->
            Log.d("localtime","$logintime is")

            if(logintime != 0.toLong()){
                val date2 = viewModel.getCurrentDateTime()
                Log.d("date2","date2 is ${date2.time} in logintime observer ")
                viewModel.isLoginSession(date2.time,logintime)
            }else{
                onSessionLogout()
                Log.d("else","else part running in locatime observer")
            }
        })

        viewModel.isloggedin.observe(this, androidx.lifecycle.Observer { isLogin->
            Log.d("response","response is $isLogin")
            if(isLogin){
                val date2 = viewModel.getCurrentDateTime()
                Log.d("date2","date2 is $date2 in isloggedin observer")
                viewModel.saveLoggedinTime(date2)
                onSessionIn()
            }else{
                onSessionLogout()
                Log.d("else","else part running in isloggedin observer")
            }
        })

        viewModel.exception.observe(this, androidx.lifecycle.Observer {exception->
            if(exception){
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        })


        Log.d("onResume", "onResume_restartActivity")
    }//end of onResume method

//    fun getCurrentDateTime(): Date {
//        return Calendar.getInstance().time
//    }

    override fun onPause() {
        Log.d("onPause", "onPauseActivity change")
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroyActivity change")
    }


    fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(R.id.loginFragment)
    }

    fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.photoFragment)
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






