package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayVideoViewModelFactory(private val videoList: List<String>, private val indexPosition: Int ):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>):T {
        return PlayVideoViewModel(videoList ,indexPosition) as T
    }
}