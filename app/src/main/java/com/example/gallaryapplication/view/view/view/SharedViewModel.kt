package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.view.view.model.GallaryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
 private val gallaryApiSerivce: GallaryApiServiceRepository,

): ViewModel() {

  private val _userImage by lazy { MutableLiveData<List<String>>(emptyList()) }
  val userImage: LiveData<List<String>> = _userImage

  private val _loading by lazy { MutableLiveData<Boolean>() }
  val loading: LiveData<Boolean> = _loading

 private val _currentUri by lazy { MutableLiveData<String>() }
 val currentUri: LiveData<String> = _currentUri

 private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
 val showControlButton: LiveData<Boolean> = _showControlButton

  private var indexPosition: Int= 0

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



 fun onPreviousImageClick() {
  if (indexPosition > 0) {
   indexPosition--
   _currentUri.postValue(_userImage.value?.get(indexPosition))
  }
 }

 fun onNextImageClick() {
  if (indexPosition < (_userImage.value?.size?.minus(1)!!)) {
   indexPosition++
   _currentUri.postValue(_userImage.value?.get(indexPosition))
  }
 }

 fun toggleControlButton() {
  _showControlButton.postValue(!(_showControlButton.value ?: false))
 }

 fun setImageUri(imageUri: String, position: Int) {
  _currentUri.value = imageUri
  indexPosition = position

 }


}//end of sharedviewmodel