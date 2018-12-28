package com.dev.nihitb06.theupdate

import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.network.requests.ArticleResponseObject
import com.dev.nihitb06.theupdate.data.network.requests.ResponseParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test

class NetworkRequestTester {

    private val json = "{\"status\":\"ok\",\"totalResults\":3217,\"articles\"" +
            ":[{\"source\":{" +
            "\"id\":null," +
            "\"name\":\"Tribunnews.com\"" +
            "},\"author\":\"Mohammad Rifan Aditya\"," +
            "\"title\":\"Harga dan Spesifikasi Honor V20 Terbaru Punya Kamera 48 MP, Saingan Berat dari Xiaomi - Tribun Style\"," +
            "\"description\":\"Honor secara resmi akan segera meluncurkan hape terbarunya dengan kamera 48 MP yaitu Honor V20 (View 20), berikut ini harga dan spesifikasinya.\"," +
            "\"url\":\"http://style.tribunnews.com/2018/12/28/harga-dan-spesifikasi-honor-v20-terbaru-punya-kamera-48-mp-saingan-berat-dari-xiaomi\"," +
            "\"urlToImage\":\"http://cdn2.tstatic.net/style/foto/bank/images/peluncuran-honor-v20-view-20.jpg\"," +
            "\"publishedAt\":\"2018-12-28T08:25:00Z\"," +
            "\"content\":\"TRIBUNSTYLE.COM - Honor secara resmi akan segera meluncurkan hape terbarunya dengan kamera 48 MP yaitu Honor V20 (View 20). Honor secara resmi akan segera meluncurkan hape terbaru mereka dengan kamera 48 MP. Hape itu sendiri adalah Honor V20 (View 20) yang te… [+1219 chars]\"" +
            "}]}"
    private val category = "technology"

    @Test
    fun checkParsedJson() {
        try {
            val jsonObject = JSONObject(json)

            if(jsonObject[ResponseParser.STATUS] == ResponseParser.STATUS_CODE_ERROR)
                return

            val articles = jsonObject[ResponseParser.ARTICLES] as JSONArray
            val articleEntities = ArrayList<ArticleEntity>()

            for (i in 0 until articles.length()) {
                val article = articles[i] as JSONObject

                articleEntities.add(ArticleEntity(
                        article[ResponseParser.AUTHOR] as String,
                        article[ResponseParser.TITLE] as String,
                        article[ResponseParser.DESCRIPTION] as String,
                        category,
                        article[ResponseParser.URL] as String,
                        article[ResponseParser.URL_TO_IMAGE] as String,
                        article[ResponseParser.PUBLISHED_AT] as String,
                        article[ResponseParser.CONTENT] as String
                ))
            }

            val code = try { jsonObject[ResponseParser.CODE] as String } catch (e: JSONException) { null }
            val message = try { jsonObject[ResponseParser.MESSAGE] as String } catch (e: JSONException) { null }

            print(ArticleResponseObject(
                    ResponseParser.STATUS_CODE_OK,
                    jsonObject[ResponseParser.TOTAL_RESULTS] as Int,
                    articleEntities,
                    code, message
            ))
        } catch (e: ClassCastException) {
            print(e.message)
        } catch (e: JSONException) {
            print(e.message)
        }
    }
}