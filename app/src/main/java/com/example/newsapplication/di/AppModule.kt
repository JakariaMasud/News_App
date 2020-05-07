package com.example.newsapplication.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {
    @Provides
    fun provideApplicationContext():Application{
        return application
    }

}