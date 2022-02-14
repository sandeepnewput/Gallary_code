package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayVideoViewModel(
    private var videoArray: Array<String>,
    private var indexposition: Int
) : ViewModel() {

    init {
        nextviewmodel()
    }


    private val _currentIndexPosition by lazy { MutableLiveData<String>() }
    val currentIndexPosition: LiveData<String> = _currentIndexPosition

    private val _isVisible by lazy { MutableLiveData<Boolean>() }
    val isVisibleInvisible: LiveData<Boolean> = _isVisible

    private val _isPlaying by lazy { MutableLiveData<Boolean>() }
    val isPlayPause: LiveData<Boolean> = _isPlaying

    private var isVisible: Boolean = false
    private var isPlaying: Boolean = false


    fun previousVideo() {

        if (indexposition > 0) {
            _currentIndexPosition.postValue(videoArray[indexposition - 1])
            indexposition--
            Log.d("insideifprev", "$indexposition")
        } else {
            _currentIndexPosition.postValue(videoArray[videoArray.size - 1])
            indexposition = videoArray.size - 1
            Log.d("insideelseprev", "$indexposition")
        }
    }//end of previousVideo function

    fun nextVideo() {

        if (indexposition < (videoArray.size - 1)) {
            _currentIndexPosition.postValue(videoArray[indexposition + 1])
            indexposition++
            Log.d("insideifnext", "$indexposition")
        } else {
            _currentIndexPosition.postValue(videoArray[0])
            indexposition = 0
            Log.d("insideelsenext", "$indexposition")
        }
    }//end of nextVideo function

    fun onCompleteVideo() {
        indexposition++
        if (indexposition < (videoArray.size)) {
            _currentIndexPosition.postValue(videoArray[indexposition])
            Log.d("insideifnext", "$indexposition")
        } else {
            indexposition = 0
            _currentIndexPosition.postValue(videoArray[0])
            Log.d("insideelsenext", "$indexposition")
        }
    }


    fun visibleInvisible() {
        _isVisible.postValue(isVisible)
        isVisible = !isVisible
    }//end of visibleInvisible

    fun isPlayPause() {
        _isPlaying.postValue(isPlaying)
        isPlaying = !isPlaying
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


    fun nextviewmodel() {
        Log.d("arrayinviewmodel", "${videoArray.size}")
        Log.d("indexinviewmodel", "$indexposition")
    }


}//end of PlayVideoViewModel