package com.example.gallaryapplication.view

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity



class PermissionLauncher {

    companion object {
        fun requestPermission(
            fragment: Fragment,
            activity: FragmentActivity,
            allowPermission:  () -> Unit,
            denyPermission: () -> Unit
        ): ActivityResultLauncher<String> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    allowPermission()
                } else {
                    val permissionRationale = (activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    })
                    when (permissionRationale) {
                        true -> Unit
                        else -> {
                            when (PackageManager.PERMISSION_GRANTED) {
                                activity?.let {
                                    ContextCompat.checkSelfPermission(
                                        it,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                                } -> {
                                    allowPermission()
                                }
                                else -> {
                                    denyPermission()
                                }
                            }
                        }
                    }

                }//end of callback else
            }
        }

    }//end of companion object

}//end of takepermissio class




