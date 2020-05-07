package com.example.newsapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.repositories.NewsRepository
import com.example.newsapplication.viewModel.NewsViewModel
import javax.inject.Inject


class NewsViewModelFactory @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return NewsViewModel(newsRepository)as T

    }

}