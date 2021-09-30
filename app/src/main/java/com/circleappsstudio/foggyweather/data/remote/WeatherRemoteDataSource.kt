package com.circleappsstudio.foggyweather.data.remote

import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
//import com.circleappsstudio.foggyweather.data.model.CurrentWeatherLocationResults
import com.circleappsstudio.foggyweather.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSource(private val webService: WebService) {

    suspend fun getCurrentWeather(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults = withContext(Dispatchers.IO) {

        webService.getCurrentWeather(
            AppConstants.API_KEY,
            location,
            airQuality
        )

    }

    /*suspend fun getCurrentWeatherLocation(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherLocationResults = withContext(Dispatchers.IO) {

        webService.getCurrentWeatherLocation(
            AppConstants.API_KEY,
            location,
            airQuality
        )

    }*/

}