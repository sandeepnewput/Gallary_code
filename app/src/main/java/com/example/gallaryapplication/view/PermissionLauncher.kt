package com.example.gallaryapplication.view


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
            ) { it ->

                val permissionAllow = it.filter {
                    it.value == true
                }.map {
                    it.key
                }
                val permissionDenied = it.filter {
                    it.value == false
                }.map {
                    it.key
                }
                if (permissionAllow.isNotEmpty()) {
                    listener.permissionAllowed(permissionAllow)
                }
                if (permissionDenied.isNotEmpty()) {
                    for (permission in permissionDenied) {
                        showRational(activity, listener, permission)
                    }
                }

            }
        }

        private fun showRational(
            activity: FragmentActivity,
            listener: Listener,
            permission: String,
        ) {

            val permissionRationale = (activity?.let { it ->
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    permission,
                )
            })
            when (permissionRationale) {
                true -> listener.showRationalForPermission(permission)
                else -> {
                    listener.permissionDenied()
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
//                    listener.permissionAllowed()
                } else {
                    val permissionRationale = (activity?.let { it ->
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        )
                    })
                    when (permissionRationale) {
                        true -> listener.showRationalForPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        else -> {
                            listener.permissionDenied()
                        }
                    }
                }//end of callback else
            }
        }
    }//end of companion object

}//end of takepermissio class






