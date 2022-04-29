package com.example.gallaryapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.model.LocalApiServiceRepository
import com.example.gallaryapplication.model.MediaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val localApiService: LocalApiServiceRepository,
):ViewModel() {

    private val _userMusic by lazy { MutableLiveData<List<MediaModel>>(emptyList()) }
    val userMusic: LiveData<List<MediaModel>> = _userMusic

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

        fun getAllUserMusic() {
        if (_userMusic.value?.isEmpty() != true) return
        _loading.postValue(true)
        viewModelScope.launch {
            val getResponse = withContext(Dispatchers.IO) { localApiService.getAllMusic() }
            if (getResponse.isNotEmpty()) {
                _userMusic.postValue(getResponse)
            }
            _loading.postValue(false)
        }
    }//end of getAllUserMusic

}