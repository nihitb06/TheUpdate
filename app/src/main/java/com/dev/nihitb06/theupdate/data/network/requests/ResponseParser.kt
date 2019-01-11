package com.dev.nihitb06.theupdate.data.network.requests

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

        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val URL_TO_IMAGE = "urlToImage"
        const val PUBLISHED_AT = "publishedAt"
        const val CONTENT = "content"

        fun parseJson(json: String, category: String): ArticleResponseObject? {
            val jsonObject = JSONObject(json)

            if(jsonObject[STATUS] == STATUS_CODE_ERROR)
                return null

            val articles = try { jsonObject[ARTICLES] as JSONArray } catch (e: JSONException) { JSONArray() }
            val articleEntities = ArrayList<ArticleEntity>()

            for (i in 0 until articles.length()) {
                val articlesString = articles[i].toString()
                val article = JSONObject(articlesString.replace("null", "\'\'"))

                articleEntities.add(ArticleEntity(
                        try { article[AUTHOR] as String } catch (e: ClassCastException) { "" },
                        try { article[ResponseParser.TITLE] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.DESCRIPTION] as String } catch (e: JSONException) { "" },
                        category,
                        try { article[ResponseParser.URL] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.URL_TO_IMAGE] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.PUBLISHED_AT] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.CONTENT] as String } catch (e: JSONException) { "" }
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