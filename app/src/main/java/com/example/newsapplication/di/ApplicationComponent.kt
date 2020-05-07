package com.example.newsapplication.di

import com.example.newsapplication.view.ArticleFragment
import com.example.newsapplication.view.HomeFragment
import com.example.newsapplication.view.SavedFragment
import com.example.newsapplication.view.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,DatabaseModule::class,NetworkModule::class,ViewModelModule::class])
interface ApplicationComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(savedFragment: SavedFragment)
    fun inject(articleFragment: ArticleFragment)
    fun inject(searchFragment: SearchFragment)


}