package com.dev.nihitb06.theupdate.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity (
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