package com.dev.nihitb06.theupdate.utilities

import android.content.Context
import android.preference.PreferenceManager
import com.dev.nihitb06.theupdate.AppExecutors
import com.dev.nihitb06.theupdate.data.ArticleRepository
import com.dev.nihitb06.theupdate.data.database.TheUpdateDatabase
import com.dev.nihitb06.theupdate.data.network.NetworkDataSource

class InjectorUtils {

    companion object {

        private fun provideRepository(context: Context): ArticleRepository {
            val executors = AppExecutors.getInstance()

            return ArticleRepository.getInstance(
                    PreferenceManager.getDefaultSharedPreferences(context.applicationContext),
                    TheUpdateDatabase.getInstance(context.applicationContext).articleDao(),
                    NetworkDataSource.getInstance(context.applicationContext, executors),
                    executors
            )
        }

        fun provideNetworkDataSource(context: Context) = NetworkDataSource.getInstance(
                context.applicationContext,
                AppExecutors.getInstance()
        ).also {
            // This call to provide repository is necessary if the app starts from a service - in this
            // case the repository will not exist unless it is specifically created.
            provideRepository(context)
        }
    }
}