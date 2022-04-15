package com.example.gallaryapplication.model

import retrofit2.Response
import javax.inject.Inject

class FlickrApiServiceRepository
@Inject constructor(private val api : MediaApi){

    suspend fun fetchAllImages(): Response<PhotoResponse> {
        return api.fetchImages()
    }

}