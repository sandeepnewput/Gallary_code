package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayVideoViewModelFactory(private val videoArray: Array<String>,private val indexposition: Int ):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayVideoViewModel(videoArray,indexposition) as T
    }
}