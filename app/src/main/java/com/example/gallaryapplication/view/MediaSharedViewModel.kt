package com.example.gallaryapplication.view

import androidx.lifecycle.*
import com.example.gallaryapplication.model.LocalApiServiceRepository
import com.example.gallaryapplication.model.MediaModel
import com.example.gallaryapplication.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MediaSharedViewModel @Inject constructor(
    private val galleryApiService: LocalApiServiceRepository,
    private val prefs: SharedPreferencesHelper
) : ViewModel() {

    private val _userImages by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userImages: LiveData<List<MediaModel>> = _userImages

    private val _userVideos by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userVideos: LiveData<List<MediaModel>> = _userVideos

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    private val _currentUri by lazy { MutableLiveData<String>() }
    val currentUri: LiveData<String> = _currentUri

    private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
    val showControlButton: LiveData<Boolean> = _showControlButton

    var currentImageIndexPosition = 0
        private set

    var currentVideoIndexPosition = 0
        private set

    fun savePermissionStatus(value: Boolean) {
        prefs.savePermissionStatus(value)
    }

    fun getMediaFiles(currentFragment: String) {

        when (currentFragment) {
            "IMAGE" -> {
                if (_userImages.value?.isEmpty() != true) return
                _loading.postValue(true)
                viewModelScope.launch {
                    val getResponse =
                        withContext(Dispatchers.IO) { galleryApiService.getAllImages() }
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
                        withContext(Dispatchers.IO) { galleryApiService.getAllVideo() }
                    if (getResponse.isNotEmpty()) {
                        _userVideos.postValue(getResponse)
                    }
                    _loading.postValue(false)
                }//end of viewModelScope
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

    fun toggleControlButton() {
        _showControlButton.postValue(!(_showControlButton.value ?: false))
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