package com.circleappsstudio.foggyweather.data.remote

import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.data.model.*
import com.circleappsstudio.foggyweather.repository.weather.WebService
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

    suspend fun getForecast(
        location: String,
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
    ): ForecastResults = withContext(Dispatchers.IO) {

        webService.getForecast(
            AppConstants.API_KEY,
            location,
            days,
            airQuality,
            alerts
        )

    }

    suspend fun getAstronomy(
        location: String,
        date: String
    ): AstronomyResults = withContext(Dispatchers.IO) {

        webService.getAstronomy(
            AppConstants.API_KEY,
            location,
            date
        )

    }

    suspend fun getAutocompleteResults(
        location: String
    ): List<Locations> = withContext(Dispatchers.IO) {

        webService.getAutocompleteResults(
            AppConstants.API_KEY,
            location
        )

    }

}