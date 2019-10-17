package com.wa82bj.findgitjobs.data.api

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkInfo @Inject constructor(private val context: Context) {

    val cm =  context.applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE)
            as (ConnectivityManager)
    val activeNetwork = cm.activeNetworkInfo

    fun isNetworkAvailable(): Boolean {
        val isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting
        return isConnected
    }
}

class OfflineException:Exception()