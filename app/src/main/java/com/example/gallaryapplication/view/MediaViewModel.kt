package com.example.gallaryapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.model.LocalApiServiceRepository
import com.example.gallaryapplication.model.MediaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val localApiService: LocalApiServiceRepository,
) :ViewModel() {

    private val _userImages by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userImages: LiveData<List<MediaModel>> = _userImages

    private val _userVideos by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userVideos: LiveData<List<MediaModel>> = _userVideos

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading


    fun getMediaFiles(currentFragment: String) {

        when (currentFragment) {
            "IMAGE" -> {
                if (_userImages.value?.isEmpty() != true) return
                _loading.postValue(true)
                viewModelScope.launch {
                    val getResponse =
                        withContext(Dispatchers.IO) { localApiService.getAllImages() }
                    if (getResponse.isNotEmpty()) {
                        _userImages.postValue(getResponse)
                    }
                    _loading.postValue(false)
                }//end of viewModelScope

            }
            else -> {
                if (_userVideos.value?.isEmpty() != true) return
                _loading.postValue(true)
                viewModelScope.launch {
                    val getResponse =
                        withContext(Dispatchers.IO) { localApiService.getAllVideo() }
                    if (getResponse.isNotEmpty()) {
                        _userVideos.postValue(getResponse)
                    }
                    _loading.postValue(false)
                }//end of viewModelScope
            }
        }

    }




}