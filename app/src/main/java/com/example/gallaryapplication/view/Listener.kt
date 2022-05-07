package com.example.gallaryapplication.view

interface Listener {
    fun permissionAllowed(permissionAllow: List<String>)
    fun permissionDenied()
    fun showRationalForPermission(permission: String)
}