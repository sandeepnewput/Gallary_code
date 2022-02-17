package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FullImageViewModelFactory(private val imageList: List<String>, private val indexPosition: Int ):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FullImageViewModel(imageList,indexPosition) as T
    }

}