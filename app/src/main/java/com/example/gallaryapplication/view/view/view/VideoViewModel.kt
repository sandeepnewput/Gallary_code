package com.example.gallaryapplication.view.view.view

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

    private val _userVideo by lazy { MutableLiveData<List<String>>(emptyList()) }
    val userVideo: LiveData<List<String>> = _userVideo

     fun getAllUserVideo() {
         if(_userVideo.value?.isEmpty() != true)return
        viewModelScope.launch {

                val getResponse1 = withContext(Dispatchers.IO) { gallaryApiSerivce.getAllVideo() }
                if (getResponse1.isNotEmpty()) {
                    _userVideo.postValue(getResponse1)
                }


        }//end of viewModelScope
    }//end of getAllUserVideo





}//end of VideoViewModel