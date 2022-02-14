package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FullImageViewModelFactory(private val imageArray: Array<String>,private val indexposition: Int ):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FullImageViewModel(imageArray,indexposition) as T
    }

}