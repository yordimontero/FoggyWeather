package com.circleappsstudio.foggyweather.repository.location

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val location: Location
) : LocationRepository {

    override suspend fun getLocation(
        context: Context
    ): List<String> = withContext(Dispatchers.IO) {
        location.getLocation(context)
    }

}