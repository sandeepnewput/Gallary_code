package com.example.gallaryapplication.view.view.view



import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.gallaryapplication.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity  :  AppCompatActivity() {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val appController = AppController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        onClickRequestPermission(View)
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
        Log.d("reference","$appController")
        Log.d("onStart", "onStart method called $appController")
    }

    override fun onStop() {
        super.onStop()

        appController?.AppLifeCycleCallback()?.onActivityStopped(this)
        Log.d("onStop", "onStop method called")
    }




    private fun onSessionLogout() {
        findNavController(R.id.fragment).navigate(R.id.loginFragment)
    }

    private fun onSessionIn() {
        findNavController(R.id.fragment).navigate(R.id.photoFragment)
    }

//    private fun onClickRequestPermission(view: View) {
//        when {
//            ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                layout.showSnackbar(
//                    view,
//                    getString(R.string.permission_granted),
//                    Snackbar.LENGTH_INDEFINITE,
//                    null
//                ) {}
//            }
//
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) -> {
//                layout.showSnackbar(
//                    view,
//                    getString(R.string.permission_required),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) {
//                    requestPermissionLauncher.launch(
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE
//                    )
//                }
//            }
//
//            else -> {
//                requestPermissionLauncher.launch(
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                )
//            }
//        }
//    }//end of function
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Log.i("Permission: ", "Granted")
//
//
//            } else {
//                Log.i("Permission: ", "Denied")
//            }
//        }

}//end of main activity






