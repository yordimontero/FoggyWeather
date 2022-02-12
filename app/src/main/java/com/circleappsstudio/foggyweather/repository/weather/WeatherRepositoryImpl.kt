package com.circleappsstudio.foggyweather.repository.weather

import com.circleappsstudio.foggyweather.data.model.*
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSourceImpl
import javax.inject.Inject

/*
    @Inject constructor(...) injects WeatherRemoteDataSource Interface.
*/
class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getCurrentWeather(
        /*
            Method to get Current Weather data.
        */
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults = dataSource.getCurrentWeather(location, airQuality)

    override suspend fun getForecast(
        /*
            Method to get Forecast data.
        */
        location: String,
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
    ): ForecastResults = dataSource.getForecast(
        location, days, airQuality, alerts
    )

    override suspend fun getAstronomy(
        /*
            Method to get Astronomy data.
        */
        location: String,
        date: String
    ): AstronomyResults = dataSource.getAstronomy(
        location,
        date
    )

    override suspend fun getAutocompleteResults(
        /*
            Method to get Autocomplete locations data.
        */
        location: String
    ): List<Locations> = dataSource.getAutocompleteResults(
        location
    )
}