package com.circleappsstudio.foggyweather.repository.location

import android.app.Activity
import android.content.Context

interface LocationRepository {
    suspend fun getLocation(context: Context, activity: Activity): List<String>
    //suspend fun getLocation(context: Context, activity: Activity): String
}