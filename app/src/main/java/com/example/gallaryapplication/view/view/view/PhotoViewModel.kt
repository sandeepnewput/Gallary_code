package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.*
import com.example.gallaryapplication.view.view.model.GallaryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val gallaryApiSerivce: GallaryApiServiceRepository
) : ViewModel() {


    private val _userImage by lazy { MutableLiveData<List<String>>(emptyList()) }
    val userImage: LiveData<List<String>> = _userImage

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading


    fun getUserImage() {
        if(_userImage.value?.isEmpty() != true)return

        _loading.postValue(true)

            viewModelScope.launch {

                val getResponse1 = withContext(Dispatchers.IO) { gallaryApiSerivce.getAllImages() }

                if (getResponse1.isNotEmpty()) {
                    _userImage.postValue(getResponse1)
                }
                _loading.postValue(false)

            }//end of viewModelScope





    }//end of getUserDetails Coroutine function




}//end of ImageViewModel