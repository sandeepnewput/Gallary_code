package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FullImageViewModel (
    private val imageList: List<String>,
    private var indexPosition: Int
) : ViewModel() {

    private val _currentUri by lazy { MutableLiveData<String>(imageList[indexPosition]) }
    val currentUri: LiveData<String> = _currentUri

    private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
    val showControlButton: LiveData<Boolean> = _showControlButton

    fun onPreviousImageClick() {
        if (indexPosition > 0) {
            indexPosition--
            _currentUri.postValue(imageList[indexPosition])
        }
    }

    fun onNextImageClick() {
        if (indexPosition < (imageList.size - 1)) {
            indexPosition++
            _currentUri.postValue(imageList[indexPosition])
        }
    }

    fun toggleControlButton() {
        _showControlButton.postValue(!(_showControlButton.value ?: false))
    }

}

