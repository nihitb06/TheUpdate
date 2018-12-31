package com.dev.nihitb06.theupdate.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.nihitb06.theupdate.AppExecutors
import com.dev.nihitb06.theupdate.data.database.ArticleDao
import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.database.HeaderArticleEntity
import com.dev.nihitb06.theupdate.data.database.ListArticleEntity
import com.dev.nihitb06.theupdate.data.network.NetworkDataSource
import java.lang.IllegalStateException

class ArticleRepository private constructor(
        private val preferences: SharedPreferences,
        private val categories: Array<String>,
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

        if(isSyncNeeded())
            syncData()
    }

    private fun isSyncNeeded() = preferences.getLong(TIME_SYNCED, 0L) + (1000*60*60*24) <= System.currentTimeMillis()

    private fun deleteOldData(category: String) = articleDao.deleteAll(category)

    private fun syncData() = appExecutors.diskIO.execute { networkDataSource.syncData() }

    fun getArticle(title: String): LiveData<ArticleEntity> {
        initializeData()
        return articleDao.getArticle(title)
    }
    fun getArticles(limit: Int): LiveData<List<HeaderArticleEntity>> {
        initializeData()

        val articles = MutableLiveData<List<HeaderArticleEntity>>()
        val mainArticles = ArrayList<HeaderArticleEntity>(categories.size*3)

        appExecutors.diskIO.execute {
            for (category in categories) {
                val articleList = articleDao.getArticles(category, limit)
                mainArticles.addAll(articleList)
            }
        }

        articles.postValue(mainArticles)

        return articles
    }
    fun getArticlesByCategory(category: String, limit: Int): LiveData<List<ListArticleEntity>> {
        initializeData()
        return articleDao.getArticlesByCategory(category, limit)
    }

    companion object {

        const val TIME_SYNCED = "Time Synced"

        @Volatile
        private var INSTANCE: ArticleRepository? = null

        fun getInstance(
                preferences: SharedPreferences,
                categories: Array<String>,
                articleDao: ArticleDao,
                requestBuilder: NetworkDataSource,
                appExecutors: AppExecutors
        ) = INSTANCE ?: synchronized(this) { INSTANCE
                ?: ArticleRepository(preferences, categories, articleDao, requestBuilder, appExecutors).also {
                    INSTANCE = it
                }
        }
    }
}