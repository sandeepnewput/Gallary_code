package com.example.gallaryapplication.view.view.di

import android.app.Application
import com.example.gallaryapplication.view.view.model.GallaryApiServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class GallaryApiModule {

    @Provides
    fun provideGallaryApiService(application: Application):GallaryApiServiceRepository{
        return GallaryApiServiceRepository(application)
    }


}//end of GallaryApiModule