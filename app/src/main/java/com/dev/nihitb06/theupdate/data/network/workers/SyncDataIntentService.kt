package com.dev.nihitb06.theupdate.data.network.workers

import android.app.IntentService
import android.content.Intent
import com.dev.nihitb06.theupdate.utilities.InjectorUtils

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class SyncDataIntentService : IntentService("SyncDataIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val networkDataSource = InjectorUtils.provideNetworkDataSource(this)
        networkDataSource.fetchData()
    }
}
