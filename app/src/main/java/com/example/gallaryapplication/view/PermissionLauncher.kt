package com.example.gallaryapplication.view

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity



class PermissionLauncher {

    companion object {
        fun requestPermission(
            fragment: Fragment,
            activity: FragmentActivity,
            listener: Listener
        ): ActivityResultLauncher<String> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                   listener.permissionAllowed()
                } else {
                    val permissionRationale = (activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    })
                    when (permissionRationale) {
                        true -> listener.showRationalForPermission(true)
                        else -> {
                             listener.showRationalForPermission(false)
                        }
                    }
                }//end of callback else
            }
        }

    }//end of companion object

}//end of takepermissio class




