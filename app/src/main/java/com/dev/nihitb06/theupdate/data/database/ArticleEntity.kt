package com.dev.nihitb06.theupdate.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity (
        @Embedded
        val source: ArticleSource,
        val author: String,
        @PrimaryKey(autoGenerate = false)
        val title: String,
        val description: String,
        val category: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
)

data class ArticleSource (val id: String, val name: String)