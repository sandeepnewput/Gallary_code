package com.example.gallaryapplication.view

import androidx.lifecycle.*
import com.example.gallaryapplication.model.MediaModel
import com.example.gallaryapplication.model.MediaType


class MediaSharedViewModel :ViewModel(){

    private val _userImages by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userImages: LiveData<List<MediaModel>> = _userImages

    private val _userVideos by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userVideos: LiveData<List<MediaModel>> = _userVideos

    private val _currentUri by lazy { MutableLiveData<String>() }
    val currentUri: LiveData<String> = _currentUri

    var currentImageIndexPosition = 0
        private set

    var currentVideoIndexPosition = 0
        private set


    fun updateMediaList(mediaType: MediaType, mediaList: List<MediaModel>) {
        when(mediaType){
            MediaType.IMAGE ->{
                if(_userImages.value?.isEmpty() != true) return
                _userImages.postValue(mediaList)
            }
            MediaType.VIDEO -> {
                if(_userVideos.value?.isEmpty() != true) return
                _userVideos.postValue((mediaList))
            }
        }
    }


    fun onPreviousImageClick() {
        _userImages.value?.let {
            if (it.isNotEmpty() && currentImageIndexPosition > 0) {
                currentImageIndexPosition--
                _currentUri.postValue(it[currentImageIndexPosition].uri)
            }
        }
    }

    fun onNextImageClick() {
        _userImages.value?.let {
            if (it.isNotEmpty() && currentImageIndexPosition < it.size.minus(1)) {
                currentImageIndexPosition++
                _currentUri.postValue(it[currentImageIndexPosition].uri)
            }
        }
    }

    fun onPreviousVideoClick() {
        _userVideos.value?.let {
            if (it.isNotEmpty() && currentVideoIndexPosition > 0) {
                currentVideoIndexPosition--
                _currentUri.postValue(it[currentVideoIndexPosition].uri)
            }
        }
    }//end of previousVideo function

    fun onNextVideoClick() {
        _userVideos.value?.let {
            if (it.isNotEmpty() && currentVideoIndexPosition < it.size.minus(1)) {
                currentVideoIndexPosition++
                _currentUri.postValue(it[currentVideoIndexPosition].uri)
            }
        }
    }//end of nextVideo function

    fun onCompleteVideo() {
        _userVideos.value?.let {
            currentVideoIndexPosition++
            if (it.isNotEmpty() && currentVideoIndexPosition < it.size) {
                _currentUri.postValue(it[currentVideoIndexPosition].uri)
            }
        }
    }


    fun setCurrentVideoUri(mediaModel: MediaModel) {
        _userVideos.value?.let {
            if (it.isNotEmpty() && it.indexOf(mediaModel) != -1) {
                _currentUri.value = mediaModel.uri
                currentVideoIndexPosition = it.indexOf(mediaModel)
            }
        }
    }

    fun setCurrentImageUri(mediaModel: MediaModel) {
        _userImages.value?.let {
            if (it.isNotEmpty() && it.indexOf(mediaModel) != -1) {
                _currentUri.value = mediaModel.uri
                currentImageIndexPosition = it.indexOf(mediaModel)
            }
        }
    }

    fun timeConversion(value: Int): String {
        val songTime: String
        val hrs = value / 3600000
        val mns = value / 60000
        val scs = value / 1000 % 60
        songTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return songTime
    }


}//end of sharedviewmodel