package com.dev.nihitb06.theupdate

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors private constructor(val diskIO: Executor, val networkIO: Executor) {

    companion object {

        @Volatile
        private var INSTANCE: AppExecutors? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3)).also {
                INSTANCE = it
            }
        }
    }
}