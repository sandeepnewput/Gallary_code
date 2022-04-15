package com.example.gallaryapplication.model


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaApi {

    @GET("?method=flickr.photos.getRecent&api_key=cfbf06ed983e80216394fb3be29f4107&user_id=195349033%40N04&per_page=50&format=json&nojsoncallback=1")
    suspend fun fetchImages(): Response<PhotoResponse>

}