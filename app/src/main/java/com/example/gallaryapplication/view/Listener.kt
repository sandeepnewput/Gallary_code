package com.example.gallaryapplication.view

interface Listener {
    fun permissionAllowed()
    fun permissionDenied()
    fun showRationalForPermission()
}