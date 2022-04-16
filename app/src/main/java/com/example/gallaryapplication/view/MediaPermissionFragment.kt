package com.example.gallaryapplication.view

import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.gallaryapplication.R
import com.google.android.material.snackbar.Snackbar


abstract class MediaPermissionFragment<viewBinding : ViewBinding> : BaseFragment<viewBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissionLauncher.launch(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }//end of onViewCreated method

    private fun onClickRequestPermission(view: View) {

        when {
            activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == true -> {
                Snackbar.make(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }.show()
            }
            else -> {
                when (PackageManager.PERMISSION_GRANTED) {
                    context?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } -> {
                        getMediaFiles()
                    }
                    else -> {

                        activity?.let {
                            val builder = AlertDialog.Builder(it)
                            builder.apply {
                                setTitle(getString(R.string.permission))
                                setMessage(
                                    getString(R.string.permission_required),
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
                                            startActivity(this)
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
    }//end of function

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMediaFiles()
            } else {
                onClickRequestPermission(binding.root)
            }
        }

    open fun getMediaFiles() = Unit


}//end of Permission