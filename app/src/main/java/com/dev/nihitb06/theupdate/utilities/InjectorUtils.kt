package com.dev.nihitb06.theupdate.utilities

import android.content.Context
import android.preference.PreferenceManager
import com.dev.nihitb06.theupdate.AppExecutors
import com.dev.nihitb06.theupdate.R
import com.dev.nihitb06.theupdate.data.ArticleRepository
import com.dev.nihitb06.theupdate.data.database.TheUpdateDatabase
import com.dev.nihitb06.theupdate.data.network.NetworkDataSource
import com.dev.nihitb06.theupdate.ui.details.DetailViewModelFactory
import com.dev.nihitb06.theupdate.ui.list.ListViewModelFactory

class InjectorUtils {

    companion object {

        private fun provideRepository(context: Context): ArticleRepository {
            val executors = AppExecutors.getInstance()

            return ArticleRepository.getInstance(
                    PreferenceManager.getDefaultSharedPreferences(context.applicationContext),
                    context.resources.getStringArray(R.array.categories),
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

        fun provideListViewModelFactory(context: Context, category: String?)
                = ListViewModelFactory(provideRepository(context.applicationContext), category)

        fun provideDetailViewModelFactory(context: Context, title: String)
                = DetailViewModelFactory(provideRepository(context.applicationContext), title)
    }
}