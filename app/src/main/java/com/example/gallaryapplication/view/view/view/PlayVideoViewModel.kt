package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayVideoViewModel(
    private var videoList: List<String>,
    private var indexPosition: Int
) : ViewModel() {

    private val _currentUri by lazy { MutableLiveData<String>(videoList[indexPosition]) }
    val currentUri: LiveData<String> = _currentUri

    private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
    val showControlButton: LiveData<Boolean> = _showControlButton

    private val _isPlayPauseVideo by lazy { MutableLiveData<Boolean>(true) }
    val isPlayPauseVideo: LiveData<Boolean> = _isPlayPauseVideo

//    private var isPlaying: Boolean = false

    fun onPreviousVideoClick() {
        if (indexPosition > 0) {
            indexPosition--
            _currentUri.postValue(videoList[indexPosition])
        }
    }//end of previousVideo function

    fun onNextVideoClick() {
        if (indexPosition < (videoList.size - 1)) {
            indexPosition++
            _currentUri.postValue(videoList[indexPosition])
        }
    }//end of nextVideo function

    fun onCompleteVideo() {
        indexPosition++
        if (indexPosition < (videoList.size)) {
            _currentUri.postValue(videoList[indexPosition])

        }
    }


    fun toggleControlButton() {
        _showControlButton.postValue(!(_showControlButton.value ?: false))

    }//end of visibleInvisible

    fun playPauseVideo() {
        _isPlayPauseVideo.postValue(!(_isPlayPauseVideo.value ?: false))
    }//end of visibleInvisible


    fun timeConversion(value: Int): String? {
        val songTime: String
        val dur = value
        val hrs = dur / 3600000
        val mns = dur / 60000 % 60000
        val scs = dur % 60000 / 1000
        songTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return songTime
    }

}//end of PlayVideoViewModel