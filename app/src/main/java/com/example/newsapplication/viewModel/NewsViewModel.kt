package com.example.newsapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.models.Article
import com.example.newsapplication.models.NewsResponse
import com.example.newsapplication.repositories.NewsRepository
import com.example.newsapplication.utils.Resource
import kotlinx.coroutines.launch

class NewsViewModel(val newsRepository: NewsRepository):ViewModel() {
    var breakingNews :MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var SearchNews :MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    init {
        getBreakingNews()
    }

    private fun getBreakingNews()=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getTopStories()
        if(response.isSuccessful){
            response.body()?.let{
                breakingNews.postValue(Resource.Success(it))
            }
        }
        else{
            breakingNews.postValue(Resource.Failure(response.message()))
        }
    }

     fun searchNewsByKeyword(query:String)=viewModelScope.launch {
        SearchNews.postValue(Resource.Loading())
        val response=newsRepository.getSearchResultByKeyword(query)
        if(response.isSuccessful){
            response.body()?.let {
                SearchNews.postValue(Resource.Success(it))
            }
        }
        else{
            SearchNews.postValue(Resource.Failure(response.message()))
        }
    }


    fun getSavedArticles()=newsRepository.getSavedArticles()
    fun insertArticle(article: Article)=viewModelScope.launch {
        newsRepository.insertArticle(article)
    }
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}