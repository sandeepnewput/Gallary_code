package com.example.gallaryapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallaryapplication.model.MediaType

class BottomNavigationViewModel:ViewModel() {

    private val _mediaType by lazy { MutableLiveData<MediaType>(MediaType.IMAGE) }
    val mediaType: LiveData<MediaType> = _mediaType

    fun updateFragment(mediaType: MediaType) {
        _mediaType.postValue(mediaType)
    }
}