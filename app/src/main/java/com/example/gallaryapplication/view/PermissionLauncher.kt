package com.example.gallaryapplication.view

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class PermissionLauncher {

    companion object {
        fun requestMultiplePermission(
            fragment: Fragment,
            activity: FragmentActivity,
            listener: Listener,
        ): ActivityResultLauncher<Array<String>> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) {
                for (entry in it.entries) {
                    if (entry.value) {
                        listener.permissionAllowed()
                    } else {
                        when (entry.key) {

                            android.Manifest.permission.READ_EXTERNAL_STORAGE -> {

                                val permissionRationale = (activity?.let { it ->
                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        it,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    )
                                })

                                when (permissionRationale) {
                                    true -> listener.showRationalForPermission()
                                    else -> {
                                        listener.permissionDenied()
                                    }
                                }
                            }

                            android.Manifest.permission.READ_CONTACTS -> {

                                val permissionRationale = (activity?.let { it ->
                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        it,
                                        android.Manifest.permission.READ_CONTACTS,
                                    )
                                })
                                when (permissionRationale) {
                                    true -> {
                                        listener.showRationalForPermission()
                                    }
                                    else -> {
                                        listener.permissionDenied()
                                    }
                                }
                            }

                            android.Manifest.permission.CAMERA -> {

                                val permissionRationale = (activity?.let { it ->
                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        it,
                                        android.Manifest.permission.READ_CONTACTS,
                                    )
                                })
                                when (permissionRationale) {
                                    true -> {
                                        listener.showRationalForPermission()
                                    }
                                    else -> {
                                        listener.permissionDenied()
                                    }
                                }
                            }


                        }//end of when
                    }//end of else

                }

            }
        }

        fun requestPermission(
            fragment: Fragment,
            activity: FragmentActivity,
            listener: Listener,
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
                        true -> listener.showRationalForPermission()
                        else -> {
                            listener.permissionDenied()
                        }
                    }
                }//end of callback else
            }
        }
    }//end of companion object

}//end of takepermissio class




