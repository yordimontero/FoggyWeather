package com.circleappsstudio.foggyweather.repository.location

import android.content.Context

interface LocationRepository {
    suspend fun getLocation(context: Context): List<String>
}