package com.dev.nihitb06.theupdate.data.network.requests

import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.database.ArticleSource
import org.json.JSONArray
import org.json.JSONObject

class ResponseParser {

    companion object {

        private const val STATUS = "status"
        private const val TOTAL_RESULTS = "totalResults"
        private const val ARTICLES = "articles"
        private const val CODE = "code"
        private const val MESSAGE = "message"

        private const val STATUS_CODE_OK = "ok"
        private const val STATUS_CODE_ERROR = "error"

        private const val SOURCE = "source"
        private const val AUTHOR = "author"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val URL = "url"
        private const val URL_TO_IMAGE = "urlToImage"
        private const val PUBLISHED_AT = "publishedAt"
        private const val CONTENT = "content"

        fun parseJson(json: String, category: String): ArticleResponseObject? {
            val jsonObject = JSONObject(json)

            if(jsonObject[STATUS] == STATUS_CODE_ERROR)
                return null

            val articles = jsonObject[ARTICLES] as JSONArray
            val articleEntities = ArrayList<ArticleEntity>()

            for (i in 0 until articles.length()) {
                val article = articles[i] as JSONObject

                articleEntities.add(ArticleEntity(
                        article[SOURCE] as ArticleSource,
                        article[AUTHOR] as String,
                        article[TITLE] as String,
                        article[DESCRIPTION] as String,
                        category,
                        article[URL] as String,
                        article[URL_TO_IMAGE] as String,
                        article[PUBLISHED_AT] as String,
                        article[CONTENT] as String
                ))
            }

            return ArticleResponseObject(
                    STATUS_CODE_OK,
                    jsonObject[TOTAL_RESULTS] as Int,
                    articleEntities,
                    jsonObject[CODE] as String,
                    jsonObject[MESSAGE] as String
            )
        }
    }
}