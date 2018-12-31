package com.dev.nihitb06.theupdate.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articleEntity: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE title=:title")
    fun getArticle(title: String): LiveData<ArticleEntity>

    @Query("SELECT title, category, urlToImage, description, publishedAt FROM articles WHERE category=:category LIMIT :limit")
    fun getArticles(category: String, limit: Int): List<HeaderArticleEntity>

    @Query("SELECT title, urlToImage, description, publishedAt FROM articles WHERE category=:category LIMIT :limit")
    fun getArticlesByCategory(category: String, limit: Int): LiveData<List<ListArticleEntity>>

    @Query("DELETE FROM articles WHERE category=:category")
    fun deleteAll(category: String)
}