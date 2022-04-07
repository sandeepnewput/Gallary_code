package com.example.gallaryapplication.view.view.model

import retrofit2.Response
import javax.inject.Inject

class FlickrApiServiceRepository
@Inject constructor(private val api : MediaApi){

    suspend fun fetchAllImages(): Response<PhotoModel> {
        return api.fetchImages()
    }

}