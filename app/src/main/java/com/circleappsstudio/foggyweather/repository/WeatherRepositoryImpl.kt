package com.circleappsstudio.foggyweather.repository

import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
import com.circleappsstudio.foggyweather.data.model.ForecastResults
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource

class WeatherRepositoryImpl(
    private val dataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getCurrentWeather(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults = dataSource.getCurrentWeather(location, airQuality)

    override suspend fun getForecast(
        location: String,
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
    ): ForecastResults = dataSource.getForecast(
        location, days, airQuality, alerts
    )

}