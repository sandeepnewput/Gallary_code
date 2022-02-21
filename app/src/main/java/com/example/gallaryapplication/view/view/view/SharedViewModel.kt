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
 private val galleryApiService: GallaryApiServiceRepository,

 ): ViewModel() {

  private val _userImage by lazy { MutableLiveData<List<String>>(emptyList()) }
  val userImage: LiveData<List<String>> = _userImage

 private val _userVideo by lazy { MutableLiveData<List<String>>(emptyList()) }
 val userVideo: LiveData<List<String>> = _userVideo

  private val _loading by lazy { MutableLiveData<Boolean>() }
  val loading: LiveData<Boolean> = _loading

 private val _currentUri by lazy { MutableLiveData<String>() }
 val currentUri: LiveData<String> = _currentUri

 private val _showControlButton by lazy { MutableLiveData<Boolean>(true) }
 val showControlButton: LiveData<Boolean> = _showControlButton

 private val _isPlayPauseVideo by lazy { MutableLiveData<Boolean>(true) }
 val isPlayPauseVideo: LiveData<Boolean> = _isPlayPauseVideo

  private var indexPosition: Int= 0



  fun getUserImage() {
   if(_userImage.value?.isEmpty() != true)return

   _loading.postValue(true)


   viewModelScope.launch {
    Log.d("getuser","getuserimage is called")
    val getResponse1 = withContext(Dispatchers.IO) { galleryApiService.getAllImages() }

    if (getResponse1.isNotEmpty()) {
     _userImage.postValue(getResponse1)
    }
    _loading.postValue(false)

   }//end of viewModelScope

  }//end of getUserDetails Coroutine function



 fun getAllUserVideo() {
  if(_userVideo.value?.isEmpty() != true)return
  viewModelScope.launch {

   val getResponse1 = withContext(Dispatchers.IO) { galleryApiService.getAllVideo() }
   if (getResponse1.isNotEmpty()) {
    _userVideo.postValue(getResponse1)
   }


  }//end of viewModelScope
 }//end of getAllUserVideo



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




 fun onPreviousVideoClick() {
  if (indexPosition > 0) {
   indexPosition--
   _currentUri.postValue(_userVideo.value?.get(indexPosition))
  }
 }//end of previousVideo function

 fun onNextVideoClick() {
  if (indexPosition < (_userVideo.value?.size?.minus(1)!!)) {
   indexPosition++
   _currentUri.postValue(_userVideo.value?.get(indexPosition))
  }
 }//end of nextVideo function

 fun onCompleteVideo() {
  indexPosition++
  if (indexPosition < (_userVideo.value?.size!!)) {
   _currentUri.postValue(_userVideo.value?.get(indexPosition))

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



 fun setCurrentUriIndexPosition(uri: String, position: Int) {
  _currentUri.value = uri
  indexPosition = position

 }


}//end of sharedviewmodel