package com.example.gallaryapplication.di

import android.app.Application
import com.example.gallaryapplication.model.FlickrApiServiceRepository
import com.example.gallaryapplication.model.LocalApiServiceRepository
import com.example.gallaryapplication.model.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


@Module
@InstallIn(ViewModelComponent::class)
class GalleryApiModule {

    private val BASE_URL = "https://api.flickr.com/services/rest/"

    @Provides
    fun provideMediaApi(): MediaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Provides
    fun provideLocalApiService(application: Application): LocalApiServiceRepository {
        return LocalApiServiceRepository(application.contentResolver)
    }

    @Provides
    fun provideFlickrApiService(mediaApi: MediaApi): FlickrApiServiceRepository {
        return FlickrApiServiceRepository(mediaApi)
    }

}//end of GallaryApiModule