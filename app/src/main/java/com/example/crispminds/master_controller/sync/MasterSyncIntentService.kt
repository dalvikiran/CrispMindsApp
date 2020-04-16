package com.example.crispminds.master_controller.sync

import android.app.IntentService
import android.content.Intent

class MasterSyncIntentService : IntentService("MasterSyncIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        SyncData.syncData(this)
    }
}