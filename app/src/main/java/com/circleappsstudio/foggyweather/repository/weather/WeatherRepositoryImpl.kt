package com.circleappsstudio.foggyweather.repository.weather

import com.circleappsstudio.foggyweather.data.model.*
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSourceImpl
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
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

    override suspend fun getAstronomy(
        location: String,
        date: String
    ): AstronomyResults = dataSource.getAstronomy(
        location,
        date
    )

    override suspend fun getAutocompleteResults(
        location: String
    ): List<Locations> = dataSource.getAutocompleteResults(
        location
    )
}