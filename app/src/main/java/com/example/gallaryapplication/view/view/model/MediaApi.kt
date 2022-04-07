package com.example.gallaryapplication.view.view.model


import retrofit2.Response
import retrofit2.http.GET

interface MediaApi {

    @GET("?method=flickr.photos.getRecent&api_key=cfbf06ed983e80216394fb3be29f4107&user_id=195349033%40N04&per_page=50&format=json&nojsoncallback=1")
    suspend fun fetchImages(): Response<PhotoModel>

}