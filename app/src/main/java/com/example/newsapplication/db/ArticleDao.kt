package com.example.newsapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapplication.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM article_table")
    fun getAllArticles():LiveData<List<Article>>

}