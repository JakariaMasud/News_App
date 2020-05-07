package com.example.newsapplication.di

import com.example.newsapplication.NewsApi
import com.example.newsapplication.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit):NewsApi{
        return retrofit.create(NewsApi::class.java)
    }

}