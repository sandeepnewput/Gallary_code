package com.example.gallaryapplication.view.view.di

import android.app.Application
import com.example.gallaryapplication.view.view.model.GalleryApiServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class GallaryApiModule {

    @Provides
    fun provideGallaryApiService(application: Application):GalleryApiServiceRepository{
        return GalleryApiServiceRepository(application)
    }


}//end of GallaryApiModule