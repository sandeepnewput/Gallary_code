package com.example.gallaryapplication.view.view.di

import android.app.Application
import com.example.gallaryapplication.view.view.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class PrefsModule {

    @Provides
    fun provideSharedPreferences(app: Application):SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }

}