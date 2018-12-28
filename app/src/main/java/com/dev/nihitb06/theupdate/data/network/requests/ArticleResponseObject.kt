package com.dev.nihitb06.theupdate.data.network.requests

import com.dev.nihitb06.theupdate.data.database.ArticleEntity

data class ArticleResponseObject (
        val status: String,
        val totalResults: Int,
        val articles: List<ArticleEntity>,
        val code: String?,
        val message: String?
)