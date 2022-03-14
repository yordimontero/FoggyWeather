package com.circleappsstudio.foggyweather.data.remote

import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.data.model.*
import com.circleappsstudio.foggyweather.data.webservice.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
    @Inject constructor(...) injects WebService Interface.
*/
class WeatherRemoteDataSourceImpl @Inject constructor(
    private val webService: WebService
) : WeatherRemoteDataSource {

    override suspend fun getCurrentWeather(
        location: String
    ): CurrentWeatherResults = withContext(Dispatchers.IO) {
        /*
            Method to get current weather data from WebService.
        */
        webService.getCurrentWeather(
            AppConstants.API_KEY,
            location,
            airQuality = false
        )

    }

    override suspend fun getForecast(
        location: String
    ): ForecastResults = withContext(Dispatchers.IO) {
        /*
            Method to get forecast data from WebService.
        */
        webService.getForecast(
            AppConstants.API_KEY,
            location,
            days = 3,
            airQuality = false,
            alerts = false
        )

    }

    override suspend fun getAstronomy(
        location: String,
        date: String
    ): AstronomyResults = withContext(Dispatchers.IO) {
        /*
            Method to get astronomy data from WebService.
        */
        webService.getAstronomy(
            AppConstants.API_KEY,
            location,
            date
        )

    }

    override suspend fun getAutocompleteResults(
        location: String
    ): List<Locations> = withContext(Dispatchers.IO) {
        /*
            Method to get autocomplete locations data from WebService.
        */
        webService.getAutocompleteResults(
            AppConstants.API_KEY,
            location
        )

    }

}