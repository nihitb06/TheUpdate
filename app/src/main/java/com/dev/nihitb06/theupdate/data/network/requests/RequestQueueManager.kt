package com.dev.nihitb06.theupdate.data.network.requests

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class RequestQueueManager private constructor(context: Context) {

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    companion object {

        @Volatile
        private var INSTANCE: RequestQueueManager? = null

        fun getInstance(context: Context) = INSTANCE
                ?: synchronized(this) {
            INSTANCE
                    ?: RequestQueueManager(context.applicationContext).also { INSTANCE = it }
        }
    }
}