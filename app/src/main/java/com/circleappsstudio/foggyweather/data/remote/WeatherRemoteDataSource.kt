package com.circleappsstudio.foggyweather.data.remote

import com.circleappsstudio.foggyweather.data.model.AstronomyResults
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
import com.circleappsstudio.foggyweather.data.model.ForecastResults
import com.circleappsstudio.foggyweather.data.model.Locations

interface WeatherRemoteDataSource {

    suspend fun getCurrentWeather(
        location: String
    ): CurrentWeatherResults

    suspend fun getForecast(
        location: String
    ): ForecastResults

    suspend fun getAstronomy(
        location: String,
        date: String
    ): AstronomyResults

    suspend fun getAutocompleteResults(
        location: String
    ): List<Locations>

}