package com.example.gallaryapplication.view.view.viewmodel

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.view.view.model.GallaryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    application: Application,
    private val gallaryApiSerivce: GallaryApiServiceRepository
):AndroidViewModel(application) {

    private val _userVideo by lazy { MutableLiveData<List<String>>() }
    val userVideo: LiveData<List<String>> = _userVideo

    var resolver = application.contentResolver

    fun getVideo() {
        getAllUserVideo()
    }

    private fun getAllUserVideo() {
        viewModelScope.launch {

                Log.d("withContext", "befor hit api")
                val getresponse1 = withContext(Dispatchers.IO) { gallaryApiSerivce.getallVideo() }
                Log.d("imagelist", "$getresponse1")
                if (getresponse1.isNotEmpty()) {
                    _userVideo.postValue(getresponse1)
                }


        }//end of viewModelScope
    }//end of getAllUserVideo





}//end of VideoViewModel