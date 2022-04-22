package com.example.gallaryapplication.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.gallaryapplication.R


class GetPermission {

    companion object {
        fun requestPermission(
            fragment: Fragment,
            context: Context,
            activity: FragmentActivity,
            isPermissionGranted: () -> Unit,
            alertUserAboutFeature: () -> Unit
        ): ActivityResultLauncher<String> {

            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    isPermissionGranted()
                } else {
                    val permissionRationale = (activity?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    })
                    when (permissionRationale) {
                        true -> {
                            alertUserAboutFeature()
                        }
                        else -> {
                            when (PackageManager.PERMISSION_GRANTED) {
                                context?.let {
                                    ContextCompat.checkSelfPermission(
                                        it,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                                } -> {
                                    isPermissionGranted()
                                }
                                else -> {

                                    activity?.let {
                                        val builder = AlertDialog.Builder(it)
                                        builder.apply {
                                            setTitle("Permission Required")
                                            setMessage(
                                                "Media access is required Permission to display images and videos"
                                            )

                                            setPositiveButton(R.string.setting,
                                                DialogInterface.OnClickListener { dialog, id ->
                                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                                        addFlags(FLAG_ACTIVITY_NEW_TASK)
                                                        data = Uri.fromParts(
                                                            "package",
                                                            "com.example.gallaryapplication",
                                                            "MediaFragment"
                                                        )
                                                        startActivity(context, this, null)
                                                    }
                                                })
                                            setNegativeButton(R.string.cancel,
                                                DialogInterface.OnClickListener { dialog, id ->
                                                    dialog.dismiss()
                                                })
                                            show()
                                        }
                                        builder.create()
                                    }//end of dialog box
                                }
                            }
                        }
                    }
                }//end of callback else
            }
        }

    }//end of companion object

}//end of takepermissio class




