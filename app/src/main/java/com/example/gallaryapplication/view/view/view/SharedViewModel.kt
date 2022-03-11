package com.example.gallaryapplication.view.view.view


import android.util.Log
import androidx.lifecycle.*
import com.example.gallaryapplication.view.view.model.GalleryApiServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
 private val galleryApiService: GalleryApiServiceRepository,

 ): ViewModel() {

  private val _userImages by lazy { MutableLiveData<List<String>>(emptyList()) }
  val userImages: LiveData<List<String>> = _userImages

 private val _userVideos by lazy { MutableLiveData<List<String>>(emptyList()) }
 val userVideos: LiveData<List<String>> = _userVideos

  private val _loading by lazy { MutableLiveData<Boolean>() }
  val loading: LiveData<Boolean> = _loading

 private val _currentUri by lazy { MutableLiveData<String>() }
 val currentUri: LiveData<String> = _currentUri

 private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
 val showControlButton: LiveData<Boolean> = _showControlButton

 private val _isPlayPauseVideo by lazy { MutableLiveData<Boolean>(true) }
 val isPlayPauseVideo: LiveData<Boolean> = _isPlayPauseVideo

  private var indexPosition:Int = 0


 fun getUserImage() {
   if(_userImages.value?.isEmpty() != true)return

   _loading.postValue(true)


   viewModelScope.launch {

    val getResponse1 = withContext(Dispatchers.IO) { galleryApiService.getAllImages() }

    if (getResponse1.isNotEmpty()) {
     _userImages.postValue(getResponse1)
    }
    _loading.postValue(false)

   }//end of viewModelScope

  }//end of getUserDetails Coroutine function



 fun getAllUserVideo() {
  if(_userVideos.value?.isEmpty() != true)return
  viewModelScope.launch {

   val getResponse1 = withContext(Dispatchers.IO) { galleryApiService.getAllVideo() }
   if (getResponse1.isNotEmpty()) {
    _userVideos.postValue(getResponse1)
   }


  }//end of viewModelScope
 }//end of getAllUserVideo



 fun onPreviousImageClick() {

  _userImages.value?.let {

   if ( it.isNotEmpty() && indexPosition > 0) {
    indexPosition--
    _currentUri.postValue(it[indexPosition])
   }
  }

 }

 fun onNextImageClick() {
  _userImages.value?.let {

   if ( it.isNotEmpty() && indexPosition < it.size.minus(1)) {
    indexPosition++
    _currentUri.postValue(it[indexPosition])
   }
  }
 }


 fun toggleControlButton() {
  _showControlButton.postValue(!(_showControlButton.value ?: false))
 }


 fun onPreviousVideoClick() {

  _userVideos.value?.let {
   if (it.isNotEmpty() && indexPosition > 0) {
    indexPosition--
    _currentUri.postValue(it[indexPosition])
   }
  }
 }//end of previousVideo function

 fun onNextVideoClick() {

  _userVideos.value?.let {
   if (it.isNotEmpty() && indexPosition < it.size.minus(1)) {
    indexPosition++
    _currentUri.postValue(it[indexPosition])
   }
  }
 }//end of nextVideo function

 fun onCompleteVideo() {

  _userVideos.value?.let {
   indexPosition++
   if (it.isNotEmpty() && indexPosition < it.size) {
    _currentUri.postValue(it[indexPosition])

   }
  }
 }


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



 fun setCurrentVideoUri(uri: String) {
  _userVideos.value?.let {
   if( it.isNotEmpty() && it.indexOf(uri) != -1) {
    _currentUri.value = uri
    indexPosition = it.indexOf(uri)
   }
  }
 }

 fun setCurrentImageUri(uri: String) {
  _userImages.value?.let {
   if(it.isNotEmpty() && it.indexOf(uri) != -1){
    _currentUri.value = uri
    indexPosition = it.indexOf(uri)
   }
  }
 }






}//end of sharedviewmodel