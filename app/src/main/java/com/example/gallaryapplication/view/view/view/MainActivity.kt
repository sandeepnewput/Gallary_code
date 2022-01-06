package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel
import java.util.*


class MainActivity : AppCompatActivity() {


    private val viewModel by lazy { ViewModelProvider(this).get(ImageVideoViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }//onCreate method ends

    override fun onResume() {
        super.onResume()
        var localtime1:Long? = 0
        viewModel.getLoggedintime()
        viewModel.localtime.observe(this, androidx.lifecycle.Observer { logintime->
            Log.d("localtime","$logintime is")

            if(logintime != 0.toLong()){
                val date2 = getCurrentDateTime()
                var diff = (date2.time - logintime)/60000
                Log.d("diff","diff is $diff")
                if(diff < 2){
                    viewModel.saveLoggedinTime(date2)
                    onSessionIn()
                }else{
                    onSessionLogout()
                    Log.d("else","else part running of flag 1")
                }
            }else{
                onSessionLogout()
                Log.d("else","else part running of flag 0")
            }


        })
        Log.d("onResume", "onResume_restartActivity")
    }//end of onResume method

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

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






