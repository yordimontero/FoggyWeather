package com.circleappsstudio.foggyweather.repository.location

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val location: Location
) : LocationRepository {

    override suspend fun getLocation(
        context: Context
    ): List<String> = withContext(Dispatchers.IO) {
        location.getLocation(context)
    }

    /*override suspend fun getLocation(
        context: Context, activity: Activity
    ): String = withContext(Dispatchers.IO) {
        location.getLocation(context, activity)
    }*/

}