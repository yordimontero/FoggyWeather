package com.circleappsstudio.foggyweather.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {

    suspend fun isNetworkAvailable() = withContext(Dispatchers.IO) {
        /*
            Method to check if there's internet connection.
        */
        try {
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAddress, 2000)
            sock.close()
            true
        } catch (e: Exception){
            false
        }
    }

}