package com.dev.nihitb06.theupdate.data.network.requests

import android.net.Uri
import java.net.URL

class NetworkUtils {

    companion object {

        private const val BASE_DOMAIN = "https://newsapi.prg/v2/top-headlines"

        private const val TOP_HEADLINES = "top-headlines"

        private const val CATEGORY = "category"
        private const val QUERY = "q"

        private const val PAGE_SIZE = "page-size"
        private const val PAGE_SIZE_VALUE = 15

        const val HTTP_HEADER_KEY = "X-Api-Key"

        fun getURLByCategory(category: String) = buildURL(category)
        fun getURLByQuery(query: String) = buildURLWithQuery(query)
        fun getURLByQuery(query: String, category: String) = buildURLWithQuery(query, category)

        private fun buildURL(category: String) = Uri.parse(BASE_DOMAIN).buildUpon()
                .appendQueryParameter(CATEGORY, category)
                .appendQueryParameter(PAGE_SIZE, "$PAGE_SIZE_VALUE")
                .build().toString()
        private fun buildURLWithQuery(query: String) = Uri.parse(BASE_DOMAIN).buildUpon()
                .appendQueryParameter(QUERY, query)
                .appendQueryParameter(PAGE_SIZE, "$PAGE_SIZE_VALUE")
                .build().toString()
        private fun buildURLWithQuery(query: String, category: String) = Uri.parse(BASE_DOMAIN).buildUpon()
                .appendQueryParameter(CATEGORY, category)
                .appendQueryParameter(QUERY, query)
                .appendQueryParameter(PAGE_SIZE, "$PAGE_SIZE_VALUE")
                .build().toString()
    }
}