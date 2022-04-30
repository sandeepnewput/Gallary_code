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
            ) {
                for (entry in it.entries) {
                    if (entry.value) {
                        listener.permissionAllowed()
                    } else {
                        when (entry.key) {

                            android.Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                showPermissionRational(activity, entry.key, listener)
                            }

                            android.Manifest.permission.READ_CONTACTS -> {
                                showPermissionRational(activity, entry.key, listener)
                            }

                        }//end of when
                    }//end of else
                }
            }
        }

        private fun showPermissionRational(
            activity: FragmentActivity,
            permission: String,
            listener: Listener,
        ) {
            val permissionRationale = (activity?.let { it ->
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    permission,
                )
            })
            when (permissionRationale) {
                true -> listener.showRationalForPermission()
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
                    listener.permissionAllowed()
                } else {
                    showPermissionRational(activity,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        listener)
                }//end of callback else
            }
        }
    }//end of companion object

}//end of takepermissio class




