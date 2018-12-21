package com.dev.nihitb06.theupdate.data.network.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dev.nihitb06.theupdate.utilities.InjectorUtils
import java.lang.Exception

class SyncDataWorker (private val context: Context, workerParameters: WorkerParameters) : Worker (context, workerParameters) {

    override fun doWork(): Result = try {
        val networkDataSource = InjectorUtils.provideNetworkDataSource(context.applicationContext)
        networkDataSource.fetchData()

        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
}