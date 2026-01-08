package com.cmt.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData

class ConnectionStateMonitor: ConnectivityManager.NetworkCallback() {
    var observer: MutableLiveData<Boolean> = MutableLiveData()

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun enable(context: Context) {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerNetworkCallback(networkRequest, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAvailable(network: Network) {
        observer.postValue(true)
    }


    override fun onLost(network: Network) {
        observer.postValue(false)
    }
}