package com.dev.nihitb06.theupdate.data.network

import android.app.Application
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.android.volley.Response
import com.dev.nihitb06.theupdate.AppExecutors
import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.network.requests.ArticleRequest
import com.dev.nihitb06.theupdate.data.network.requests.NetworkUtils
import com.dev.nihitb06.theupdate.data.network.requests.RequestQueueManager
import com.dev.nihitb06.theupdate.data.network.workers.SyncDataIntentService
import com.dev.nihitb06.theupdate.data.network.workers.SyncDataWorker
import java.util.concurrent.TimeUnit
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.ArticleRepository

class NetworkDataSource private constructor(private val context: Application, private val appExecutors: AppExecutors) {

    val downloadedArticleResponse: MutableLiveData<List<ArticleEntity>> = MutableLiveData()

    fun fetchData() {
        appExecutors.networkIO.execute {
            val requestQueueManager = RequestQueueManager.getInstance(context)

            val categories = NetworkUtils.CATEGORIES

            categories.iterator().forEach {
                val request = ArticleRequest(
                        NetworkUtils.getURLByCategory(it),
                        it,
                        context.getString(R.string.api_key),
                        Response.Listener { response ->
                            downloadedArticleResponse.postValue(response.articles)

                            PreferenceManager.getDefaultSharedPreferences(context)
                                    .edit()
                                    .putLong(ArticleRepository.TIME_SYNCED, System.currentTimeMillis())
                                    .apply()
                        },
                        Response.ErrorListener { /*Do Nothing*/ }
                )

                requestQueueManager.addToRequestQueue(request)
            }
        }
    }

    fun scheduleDataSync() {
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<SyncDataWorker>(24, TimeUnit.HOURS)
                        .setConstraints(
                                Constraints.Builder()
                                        .setRequiredNetworkType(NetworkType.CONNECTED)
                                        .setRequiresStorageNotLow(true)
                                        .build()
                        )
                        .build()
        )
    }

    fun syncData() { context.startService(Intent(context, SyncDataIntentService::class.java)) }

    companion object {

        private const val WORK_NAME = "TheUpdateSyncDataWork"

        @Volatile
        private var INSTANCE: NetworkDataSource? = null

        fun getInstance(context: Context, appExecutors: AppExecutors) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NetworkDataSource(context.applicationContext as Application, appExecutors).also { INSTANCE = it }
        }
    }
}