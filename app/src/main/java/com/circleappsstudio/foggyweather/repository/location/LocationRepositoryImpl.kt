package com.circleappsstudio.foggyweather.repository.location

import android.content.Context
import com.circleappsstudio.foggyweather.application.location.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
    @Inject constructor(...) injects LocationUtils Interface.
*/
class LocationRepositoryImpl @Inject constructor(
    private val locationUtils: LocationUtils
): LocationRepository {

    override fun requestLocation(context: Context) {
        /*
            Method to request current location from GPS.
        */
        locationUtils.requestLocation(context)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getLocation(
        context: Context
    ): List<String> = withContext(Dispatchers.IO) {
        /*
            Method to get current location (latitude & longitude) from GPS.
        */
        locationUtils.getLocation(context)
    }
}