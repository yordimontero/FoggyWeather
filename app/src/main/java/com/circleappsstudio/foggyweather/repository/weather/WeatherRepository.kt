package com.circleappsstudio.foggyweather.repository.weather

import com.circleappsstudio.foggyweather.data.model.*

interface WeatherRepository {

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