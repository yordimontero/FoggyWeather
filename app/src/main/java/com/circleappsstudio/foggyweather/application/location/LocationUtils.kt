package com.circleappsstudio.foggyweather.application.location

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface LocationUtils {

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context): List<String>

}