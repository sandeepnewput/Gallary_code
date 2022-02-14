package com.example.gallaryapplication.view.view.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject



class FullImageViewModel @Inject constructor (
       private var imageArray: Array<String> ,
      private  var indexposition: Int
    ): ViewModel() {


    init {
        nextviewmodel()
    }

    private val _currentIndexPosition by lazy { MutableLiveData<String>() }
    val currentIndexPosition: LiveData<String> = _currentIndexPosition

    private val _isVisible by lazy { MutableLiveData<Boolean>() }
    val isVisibleInvisible: LiveData<Boolean> = _isVisible

    var isVisible: Boolean = false

    fun previousImage() {

        if(indexposition > 0) {
            _currentIndexPosition.postValue(imageArray[indexposition-1])
            indexposition--
            Log.d("insideifprev","$indexposition")
        } else {
            _currentIndexPosition.postValue(imageArray[imageArray.size-1])
            indexposition = imageArray.size-1
            Log.d("insideelseprev","$indexposition")
        }
    }//end of previousImage function

    fun nextImage() {

        if (indexposition < (imageArray.size - 1)) {
            _currentIndexPosition.postValue(imageArray[indexposition+1])
            indexposition++
            Log.d("insideifnext","$indexposition")
        } else {
            _currentIndexPosition.postValue(imageArray[0])
            indexposition = 0
            Log.d("insideelsenext","$indexposition")
        }
    }//end of nextImage function


    fun visibleInvisible() {
        _isVisible.postValue(isVisible)
        isVisible = !isVisible
    }//end of visibleInvisible




    fun nextviewmodel(){
        Log.d("arrayinviewmodel","${imageArray.size}")
        Log.d("indexinviewmodel","$indexposition")
    }




}//end of FullImageViewModel