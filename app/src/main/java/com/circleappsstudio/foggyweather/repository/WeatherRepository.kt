package com.circleappsstudio.foggyweather.repository

import com.circleappsstudio.foggyweather.data.model.AstronomyResults
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
import com.circleappsstudio.foggyweather.data.model.ForecastResults

interface WeatherRepository {

    suspend fun getCurrentWeather(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults

    suspend fun getForecast(
        location: String,
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
    ): ForecastResults

    suspend fun getAstronomy(
        location: String,
        date: String
    ): AstronomyResults

}