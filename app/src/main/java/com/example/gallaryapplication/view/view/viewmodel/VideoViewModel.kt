package com.example.gallaryapplication.view.view.viewmodel

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.example.gallaryapplication.view.view.model.GallaryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(

    private val gallaryApiSerivce: GallaryApiServiceRepository
):ViewModel() {

    private val _userVideo by lazy { MutableLiveData<List<String>>() }
    val userVideo: LiveData<List<String>> = _userVideo



    fun getVideo() {
        getAllUserVideo()
    }

    private fun getAllUserVideo() {
        viewModelScope.launch {

                Log.d("withContext", "befor hit api")
                val getResponse1 = withContext(Dispatchers.IO) { gallaryApiSerivce.getAllVideo() }
                Log.d("imagelist", "$getResponse1")
                if (getResponse1.isNotEmpty()) {
                    _userVideo.postValue(getResponse1)
                }


        }//end of viewModelScope
    }//end of getAllUserVideo





}//end of VideoViewModel