package com.example.gallaryapplication.view.view.viewmodel

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


//class ImageVideoViewModel(application:Application):AndroidViewModel(application) {
@HiltViewModel
class ImageVideoViewModel @Inject constructor(
    application:Application,
    private val prefs:SharedPreferencesHelper
):AndroidViewModel(application) {



    val userImage by lazy { MutableLiveData<List<String>>() }
    val userVideo by lazy { MutableLiveData<List<String>>() }
    val loading by lazy {MutableLiveData<Boolean>()}
    val localtime by lazy { MutableLiveData<Long>() }
    val isloggedin by lazy {MutableLiveData<Boolean>()}
    val exception by lazy {MutableLiveData<Boolean>()}


    var resolver = application.contentResolver
//    private val prefs =  SharedPreferencesHelper(getApplication())

    fun saveLoggedinTime(value: Date){
     prefs.saveLoggedinTime(value)
    }

    fun getLoggedintime(){
        val time: Long = prefs.getLoggedinTime()
        Log.d("value","$time inside getFlagvalue")
        localtime.value = time
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

  fun isLoginSession(latesttime:Long,logintime:Long) {
      try {
          var diff = (latesttime - logintime)/60000
          Log.d("diff","diff is $diff")
          Log.d("withContext","in isLoginSession function")
          isloggedin.value = diff < 2
      }catch (e:NullPointerException){
          Log.d("exception","Exception is comming")
          e.printStackTrace()
          exception.value = true
      }catch (ed:Exception){
          Log.d("exception","Exception is comming")
          ed.printStackTrace()
          exception.value = true
      }
  }//end of isLoginSession

    fun getImage(){
        loading.value = true
        getUserImage()
    }

    private fun getUserImage() {

        viewModelScope.launch{
            try {

                Log.d("withContext","befor hit api")
                val getresponse1  = withContext(Dispatchers.IO){getallImages()}
                Log.d("imagelist","$getresponse1")
                    if(getresponse1.isNotEmpty()){
                        userImage.postValue(getresponse1)
                        loading.value = false
                    }
            }catch (e:Exception){
                Log.d("exception","Exception is comming")
                e.printStackTrace()


            }

        }//end of viewModelScope

    }//end of getUserDetails Coroutine function

    private suspend  fun getallImages(): List<String> {

        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        val contacts = ArrayList<String>()
        cursor?.use {

            if (cursor != null)
            {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext())
                {
                    contacts.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idColumn)
                        ).toString()
                    )
                }
            }
            else
            {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return contacts
    }


    fun getVideo(){
        getAllUserVideo()
    }

    private fun   getAllUserVideo(){
        viewModelScope.launch{
            try {

                Log.d("withContext","befor hit api")
                val getresponse1  = withContext(Dispatchers.IO){getallVideo()}
                Log.d("imagelist","$getresponse1")
                if(getresponse1.isNotEmpty()){
                    userVideo.postValue(getresponse1)
                }
            }catch (e:Exception){
                Log.d("exception","Exception is comming")
                e.printStackTrace()


            }

        }//end of viewModelScope
    }//end of getAllUserVideo

    private suspend  fun getallVideo(): List<String> {

        val imageProjection = arrayOf(
            MediaStore.Video.Media._ID
        )

        val imageSortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        val contacts = ArrayList<String>()
        cursor?.use {

            if (cursor != null)
            {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                while (cursor.moveToNext())
                {
                    contacts.add(
                        ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idColumn)
                        ).toString()
                    )
                }
            }
            else
            {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return contacts
    }




}//end of ImageVideoViewModel




