package com.example.gallaryapplication.view.view.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel


class MainActivity : AppCompatActivity(),LogoutListener {

    private val myapp = MyApp()
     private val viewModel by lazy { ViewModelProvider(this).get(ImageVideoViewModel::class.java) }
    var localtime1:Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate","oncreate called")
        setContentView(R.layout.activity_main)

        viewModel.getLoggedintime()

        viewModel.localtime.observe(this, Observer { localtime->
            Log.d("localtime","$localtime is")
            localtime1 = localtime
        })


        viewModel.getFlagvalue()
        viewModel.flagValue.observe(this, Observer { flag ->
            if(flag == 1){
                myapp?.registerSessionListener(this)
                myapp?.startUserSession()
                val navController = findNavController(R.id.fragment)
                navController.navigate(R.id.photoFragment)

            }else{
                myapp?.registerSessionListener(this)
//                myapp?.startUserSession()
                val navController = findNavController(R.id.fragment)
                navController.navigate(R.id.loginFragment)
            }
        })



    }//end of onCreate method

    override fun onSessionLogout() {
        val navController = findNavController(R.id.fragment)
        navController.navigate(R.id.loginFragment)
    }

    override fun matchtime(time: Long) {

        viewModel.getLoggedintime()

        viewModel.localtime.observe(this, Observer { localtime->
            Log.d("localtime","$localtime is")
            localtime1 = localtime
        })


        Log.d("localtime","$localtime1 is in matchtime")
//            localtime1 = localtime

        Log.d("localtime","$localtime1 is")
        var difftime  = (time - localtime1!!)/60000
        var minute = difftime/60000
        if(difftime < 1){
            Toast.makeText(this,"your session is expired in ${1-difftime}", Toast.LENGTH_LONG).show()
        }else{
            onSessionLogout()
            viewModel.updateflag(0)
            viewModel.updateLoggedinTime(0)
            myapp?.cancelTimer()
        }

    }//end of matchtime
//
//    override fun onUserInteraction() {
//        super.onUserInteraction()
////        myapp.onUserInteracted()
//    }
//
//     override fun onResume() {
//         super.onResume()
//         Log.d("onResume","app run in resume state")
////         myapp.startUserSession()
//     }
//
//     override fun onPause() {
//         super.onPause()
//         Log.d("onPause","app run in onPause state")
//     }
//
//     override fun onStop() {
//         super.onStop()
//         myapp.startUserSession()
//         Log.d("onStop","app run in onStop state")
//     }
//
//     override fun onDestroy() {
//         super.onDestroy()
//         myapp.startUserSession()
//         Log.d("onDestroy","app run in onDestroy state")
//     }
 }//end of main activity






