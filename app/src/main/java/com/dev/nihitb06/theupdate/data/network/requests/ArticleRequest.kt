package com.dev.nihitb06.theupdate.data.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.nio.charset.Charset

class ArticleRequest (
        url: String,
        private val category: String,
        private val apiKey: String,
        private val listener: Response.Listener<ArticleResponseObject>,
        errorListener: Response.ErrorListener
) : Request<ArticleResponseObject> (Method.GET, url, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ArticleResponseObject> = try {
        val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        Response.success(ResponseParser.parseJson(json, category), HttpHeaderParser.parseCacheHeaders(response))
    } catch (e: UnsupportedEncodingException) {
        Response.error<ArticleResponseObject>(ParseError(e))
    } catch (e: Exception) {
        Response.error<ArticleResponseObject>(ParseError(e))
    }

    override fun deliverResponse(response: ArticleResponseObject?) = listener.onResponse(response)

    override fun getHeaders() = HashMap<String, String>().apply {
        put(NetworkUtils.HTTP_HEADER_KEY, apiKey)
    }
}