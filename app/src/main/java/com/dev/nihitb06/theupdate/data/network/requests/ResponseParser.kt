package com.dev.nihitb06.theupdate.data.network.requests

import android.util.Log
import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ResponseParser {

    companion object {

        const val STATUS = "status"
        const val TOTAL_RESULTS = "totalResults"
        const val ARTICLES = "articles"
        const val CODE = "code"
        const val MESSAGE = "message"

        const val STATUS_CODE_OK = "ok"
        const val STATUS_CODE_ERROR = "error"

        const val SOURCE = "source"
        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val URL_TO_IMAGE = "urlToImage"
        const val PUBLISHED_AT = "publishedAt"
        const val CONTENT = "content"

        private const val ID = "id"
        private const val NAME = "name"

        fun parseJson(json: String, category: String): ArticleResponseObject? {
            val jsonObject = JSONObject(json)

            if(jsonObject[STATUS] == STATUS_CODE_ERROR)
                return null

            val articles = jsonObject[ARTICLES] as JSONArray
            val articleEntities = ArrayList<ArticleEntity>()

            for (i in 0 until articles.length()) {
                val article = articles[i] as JSONObject

                articleEntities.add(ArticleEntity(
                        try { article[AUTHOR] as String } catch (e: ClassCastException) { "" },
                        article[TITLE] as String,
                        article[DESCRIPTION] as String,
                        category,
                        article[URL] as String,
                        article[URL_TO_IMAGE] as String,
                        article[PUBLISHED_AT] as String,
                        article[CONTENT] as String
                ))
            }

            val code = try { jsonObject[CODE] as String } catch (e: JSONException) { null }
            val message = try { jsonObject[MESSAGE] as String } catch (e: JSONException) { null }

            return ArticleResponseObject(
                    STATUS_CODE_OK,
                    jsonObject[TOTAL_RESULTS] as Int,
                    articleEntities,
                    code, message
            )
        }
    }
}