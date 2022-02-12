package com.circleappsstudio.foggyweather.repository.location

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
    @Inject constructor(...) injects LocationUtils Class.
*/
class LocationRepositoryImpl @Inject constructor(
    private val locationUtils: LocationUtils
): LocationRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getLocation(
        context: Context
    ): List<String> = withContext(Dispatchers.IO) {
        /*
            Method to get current location from GPS.
        */
        locationUtils.getLocation(context)
    }

}