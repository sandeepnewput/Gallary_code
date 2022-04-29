package com.example.gallaryapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.model.FlickrApiServiceRepository
import com.example.gallaryapplication.model.PhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlickrSharedViewModel @Inject constructor(
    private val flickrApiService: FlickrApiServiceRepository
):ViewModel() {

    private val _flickrImage by lazy { MutableLiveData<List<PhotoItem>>(emptyList()) }
    val flickrImage: LiveData<List<PhotoItem>> = _flickrImage



    fun getPhotosFromFlicker() {
        if(_flickrImage.value?.isEmpty() != true) return
        viewModelScope.launch {

            val response = withContext(Dispatchers.IO) { flickrApiService.fetchAllImages() }
            if (response.isSuccessful) {
                response.body()?.let {
                    _flickrImage.postValue(it.photo.photos)
                }
            }else _flickrImage.postValue(emptyList())
        }
    }

}//end of FlickrSharedViewmodel