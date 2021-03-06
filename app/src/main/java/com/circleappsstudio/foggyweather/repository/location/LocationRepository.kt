package com.circleappsstudio.foggyweather.repository.location

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface LocationRepository {
    @ExperimentalCoroutinesApi
    suspend fun getLocation(context: Context): List<String>
}