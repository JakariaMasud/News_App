package com.example.newsapplication.repositories

import com.example.newsapplication.db.ArticleDao
import com.example.newsapplication.models.Article
import com.example.newsapplication.NewsApi
import javax.inject.Inject


class NewsRepository  @Inject constructor(private val articleDao: ArticleDao, private val newsApi: NewsApi) {
    //Room database
    suspend fun insertArticle(article: Article)=articleDao.insertArticle(article)
    suspend fun deleteArticle(article: Article)=articleDao.deleteArticle(article)
    fun getSavedArticles()=articleDao.getAllArticles()

    //Retrofit call
    suspend fun getTopStories()=newsApi.getTopStories()
    suspend fun getSearchResultByKeyword(query:String)=newsApi.getSearchResultByKeyword(query)




}