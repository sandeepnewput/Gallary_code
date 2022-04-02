package com.example.gallaryapplication.view.view.view

import androidx.lifecycle.*
import com.example.gallaryapplication.view.view.model.MusicModel
import com.example.gallaryapplication.view.view.model.GalleryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val galleryApiService: GalleryApiServiceRepository,
) : ViewModel() {

    private val _userImages by lazy { MutableLiveData<List<String>>(emptyList()) }
    val userImages: LiveData<List<String>> = _userImages

    private val _userVideos by lazy { MutableLiveData<List<String>>(emptyList()) }
    val userVideos: LiveData<List<String>> = _userVideos

    private val _userMusic by lazy { MutableLiveData<List<MusicModel>>(emptyList()) }
    val userMusic: LiveData<List<MusicModel>> = _userMusic

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    private val _currentUri by lazy { MutableLiveData<String>() }
    val currentUri: LiveData<String> = _currentUri

    private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
    val showControlButton: LiveData<Boolean> = _showControlButton

    private val _isPlayPauseVideo by lazy { MutableLiveData<Boolean>(true) }
    val isPlayPauseVideo: LiveData<Boolean> = _isPlayPauseVideo

    private val _isPlayPauseMusic by lazy { MutableLiveData<Boolean>(true) }
    val isPlayPauseMusic: LiveData<Boolean> = _isPlayPauseMusic

    private val _fragmentId by lazy { MutableLiveData<Int>(1) }
    val fragmentId: LiveData<Int> = _fragmentId

    var currentImageIndexPosition = 0
        private set

    var currentVideoIndexPosition = 0
        private set

    var currentMusicIndexPosition = 0
        private set

    fun getAllUserImage() {
        if (_userImages.value?.isEmpty() != true) return
        _loading.postValue(true)
        viewModelScope.launch {
            val getResponse = withContext(Dispatchers.IO) { galleryApiService.getAllImages() }
            if (getResponse.isNotEmpty()) {
                _userImages.postValue(getResponse)
            }
            _loading.postValue(false)
        }//end of viewModelScope

    }//end of getUserDetails Coroutine function

    fun getAllUserVideo() {
        if (_userVideos.value?.isEmpty() != true) return
        viewModelScope.launch {
            val getResponse = withContext(Dispatchers.IO) { galleryApiService.getAllVideo() }
            if (getResponse.isNotEmpty()) {
                _userVideos.postValue(getResponse)
            }
        }//end of viewModelScope
    }//end of getAllUserVideo

    fun getAllUserMusic() {
        if (_userMusic.value?.isEmpty() != true) return
        _loading.postValue(true)
        viewModelScope.launch {
            val getResponse = withContext(Dispatchers.IO) { galleryApiService.getAllMusic() }
            if (getResponse.isNotEmpty()) {
                _userMusic.postValue(getResponse)
            }
            _loading.postValue(false)
        }
    }//end of getAllUserMusic


    fun onPreviousImageClick() {
        _userImages.value?.let {
            if (it.isNotEmpty() && currentImageIndexPosition > 0) {
                currentImageIndexPosition--
                _currentUri.postValue(it[currentImageIndexPosition])
            }
        }
    }

    fun onNextImageClick() {
        _userImages.value?.let {
            if (it.isNotEmpty() && currentImageIndexPosition < it.size.minus(1)) {
                currentImageIndexPosition++
                _currentUri.postValue(it[currentImageIndexPosition])
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
                _currentUri.postValue(it[currentVideoIndexPosition])
            }
        }
    }//end of previousVideo function

    fun onNextVideoClick() {
        _userVideos.value?.let {
            if (it.isNotEmpty() && currentVideoIndexPosition < it.size.minus(1)) {
                currentVideoIndexPosition++
                _currentUri.postValue(it[currentVideoIndexPosition])
            }
        }
    }//end of nextVideo function

    fun playPauseVideo() {
        _isPlayPauseVideo.postValue(!(_isPlayPauseVideo.value ?: false))
    }//end of visibleInvisible

    fun onCompleteVideo() {
        _userVideos.value?.let {
            currentVideoIndexPosition++
            if (it.isNotEmpty() && currentVideoIndexPosition < it.size) {
                _currentUri.postValue(it[currentVideoIndexPosition])
            }
        }
    }

    fun onNextMusicClick() {
        _userMusic.value?.let {
            if (it.isNotEmpty() && currentMusicIndexPosition < it.size.minus(1)) {
                currentMusicIndexPosition++
                _currentUri.postValue(it[currentMusicIndexPosition].uri)

            }
        }
    }

    fun onPreviousMusicClick() {
        _userMusic.value?.let {
            if (it.isNotEmpty() && currentMusicIndexPosition > 0) {
                currentMusicIndexPosition--
                _currentUri.postValue(it[currentMusicIndexPosition].uri)
            }

        }
    }

    fun onCompleteMusic() {
        _userMusic.value?.let {
            currentMusicIndexPosition++
            if (it.isNotEmpty() && currentMusicIndexPosition < it.size) {
                _currentUri.postValue(it[currentMusicIndexPosition].uri)
            }
        }
    }

    fun playPauseMusic() {
        _isPlayPauseMusic.postValue(!(_isPlayPauseMusic.value ?: false))
    }//end of visibleInvisible


    fun setCurrentVideoUri(uri: String) {
        _userVideos.value?.let {
            if (it.isNotEmpty() && it.indexOf(uri) != -1) {
                _currentUri.value = uri
                currentVideoIndexPosition = it.indexOf(uri)
            }
        }
    }

    fun setCurrentImageUri(uri: String) {
        _userImages.value?.let {
            if (it.isNotEmpty() && it.indexOf(uri) != -1) {
                _currentUri.value = uri
                currentImageIndexPosition = it.indexOf(uri)
            }
        }
    }

    fun setCurrentMusicUri(musicModel: MusicModel) {
        _userMusic.value?.let {
            if (it.isNotEmpty() && it.indexOf(musicModel) != -1) {
                _currentUri.value = musicModel.uri
                currentMusicIndexPosition = it.indexOf(musicModel)
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

    fun updateFragment(fragmentId: Int) {
        _fragmentId.value = fragmentId
    }
}//end of sharedviewmodel