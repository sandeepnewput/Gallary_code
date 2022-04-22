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
class MusicSharedViewModel @Inject constructor(
    private val localApiService: LocalApiServiceRepository,
): ViewModel(){

    private val _userMusic by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userMusic: LiveData<List<MediaModel>> = _userMusic

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    private val _currentUri by lazy { MutableLiveData<String>() }
    val currentUri: LiveData<String> = _currentUri

    var currentMusicIndexPosition = 0
        private set

    fun getAllUserMusic() {
        if (_userMusic.value?.isEmpty() != true) return
        _loading.postValue(true)
        viewModelScope.launch {
            val getResponse = withContext(Dispatchers.IO) { localApiService.getAllMusic() }
            if (getResponse.isNotEmpty()) {
                _userMusic.postValue(getResponse)
            }
            _loading.postValue(false)
        }
    }//end of getAllUserMusic


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


    fun setCurrentMusicUri(mediaModel: MediaModel) {
        _userMusic.value?.let {
            if (it.isNotEmpty() && it.indexOf(mediaModel) != -1) {
                _currentUri.value = mediaModel.uri
                currentMusicIndexPosition = it.indexOf(mediaModel)
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


}//end of musicsharedviewmodel