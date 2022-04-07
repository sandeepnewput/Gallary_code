package com.example.gallaryapplication.view.view.di

import android.app.Application
import com.example.gallaryapplication.view.view.model.GalleryApiServiceRepository
import com.example.gallaryapplication.view.view.model.MediaApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


@Module
@InstallIn(ViewModelComponent::class)
class GallaryApiModule {

    private val BASE_URL = "https://api.flickr.com/services/rest/"

    @Provides
    fun provideMediaApi(): MediaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Provides
    fun provideGalleryApiService(application: Application,mediaApi: MediaApi): GalleryApiServiceRepository {
        return GalleryApiServiceRepository(application,mediaApi)
    }

}//end of GallaryApiModule