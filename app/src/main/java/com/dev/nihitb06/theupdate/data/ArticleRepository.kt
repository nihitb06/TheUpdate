package com.dev.nihitb06.theupdate.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.dev.nihitb06.theupdate.AppExecutors
import com.dev.nihitb06.theupdate.data.database.ArticleDao
import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.database.ListArticleEntity
import com.dev.nihitb06.theupdate.data.network.NetworkDataSource

class ArticleRepository private constructor(
        private val preferences: SharedPreferences,
        private val articleDao: ArticleDao,
        private val networkDataSource: NetworkDataSource,
        private val appExecutors: AppExecutors
) {
    private var isInitialized = false

    init {
        val networkData: LiveData<List<ArticleEntity>> = networkDataSource.downloadedArticleResponse
        networkData.observeForever { newArticle -> appExecutors.diskIO.execute {
            deleteOldData(newArticle[0].category)

            articleDao.insertArticles(newArticle)
        } }
    }

    @Synchronized
    private fun initializeData() {
        if(isInitialized) return
        isInitialized = true

        networkDataSource.scheduleDataSync()

        appExecutors.diskIO.execute {
            if(isSyncNeeded())
                networkDataSource.syncData()
        }
    }

    private fun isSyncNeeded() = preferences.getLong(TIME_SYNCED, 0L) + (1000*60*60*24) <= System.currentTimeMillis()

    private fun deleteOldData(category: String) = articleDao.deleteAll(category)

    fun syncData() = appExecutors.diskIO.execute { networkDataSource.syncData() }

    fun getArticle(title: String): LiveData<ArticleEntity> {
        initializeData()
        return articleDao.getArticle(title)
    }
    fun getArticles(limit: Int): LiveData<List<ListArticleEntity>> {
        initializeData()
        return articleDao.getArticles(limit)
    }
    fun getArticlesByCategory(category: String, limit: Int): LiveData<List<ListArticleEntity>> {
        initializeData()
        return articleDao.getArticlesByCategory(category, limit)
    }
    fun getArticlesByQuery(query: String): LiveData<List<ListArticleEntity>> {
        initializeData()
        return articleDao.getArticlesByQuery("%${query.replace(' ', '%')}%")
    }

    companion object {

        const val TIME_SYNCED = "Time Synced"

        @Volatile
        private var INSTANCE: ArticleRepository? = null

        fun getInstance(preferences: SharedPreferences, articleDao: ArticleDao, requestBuilder: NetworkDataSource, appExecutors: AppExecutors) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ArticleRepository(preferences, articleDao, requestBuilder, appExecutors).also {
                        INSTANCE = it
                    }
                }
    }
}