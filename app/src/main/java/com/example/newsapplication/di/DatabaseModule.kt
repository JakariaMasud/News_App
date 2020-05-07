package com.example.newsapplication.di

import android.app.Application
import androidx.room.Room
import com.example.newsapplication.db.AppDatabase
import com.example.newsapplication.db.ArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        val database= Room.databaseBuilder(application,
            AppDatabase::class.java,"news_database")
            .fallbackToDestructiveMigration()
            .build()
        return database
    }

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.getArticleDao()
    }


}