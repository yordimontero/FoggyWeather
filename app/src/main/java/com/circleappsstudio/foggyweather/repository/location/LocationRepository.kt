package com.circleappsstudio.foggyweather.repository.location

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface LocationRepository {

    fun requestLocation(context: Context)

    @ExperimentalCoroutinesApi
    suspend fun getLocation(context: Context): List<String>

}