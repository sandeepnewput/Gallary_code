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
class ImageViewModel @Inject constructor(
    private val gallaryApiSerivce: GallaryApiServiceRepository
) : ViewModel() {


    private val _userImage by lazy { MutableLiveData<List<String>>() }
    val userImage: LiveData<List<String>> = _userImage

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading



    fun getImage() {
        _loading.value = true
        getUserImage()
    }

    private fun getUserImage() {

        viewModelScope.launch {

                Log.d("withContext", "befor hit api")
                val getResponse1 = withContext(Dispatchers.IO) { gallaryApiSerivce.getAllImages() }
                Log.d("imagelist", "$getResponse1")
                if (getResponse1.isNotEmpty()) {
                    _userImage.postValue(getResponse1)
                    _loading.value = false
                }


        }//end of viewModelScope

    }//end of getUserDetails Coroutine function




}//end of ImageViewModel