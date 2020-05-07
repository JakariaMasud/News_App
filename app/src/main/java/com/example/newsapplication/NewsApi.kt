package com.example.newsapplication

import com.example.newsapplication.models.NewsResponse
import com.example.newsapplication.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopStories(
        @Query("country")
        country:String="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @Query("country")
        country:String="us",
        @Query("page")
        pageNumber:Int=1,
        @Query("category")
        category:String="business",
        @Query("apikey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

    @GET("everything")
    suspend fun getSearchResultByKeyword(
        @Query("q")
        searchWord:String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apiKey:String=API_KEY
    ):Response<NewsResponse>

}