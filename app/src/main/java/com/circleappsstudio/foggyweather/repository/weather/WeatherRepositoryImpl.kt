package com.circleappsstudio.foggyweather.repository.weather

import com.circleappsstudio.foggyweather.data.model.*
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource
import javax.inject.Inject

/*
    @Inject constructor(...) injects WeatherRemoteDataSource Interface.
*/
class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getCurrentWeather(
        /*
            Method to get current weather data.
        */
        location: String
    ): CurrentWeatherResults = dataSource.getCurrentWeather(location)

    override suspend fun getForecast(
        /*
            Method to get forecast data.
        */
        location: String
    ): ForecastResults = dataSource.getForecast(
        location
    )

    override suspend fun getAstronomy(
        /*
            Method to get astronomy data.
        */
        location: String,
        date: String
    ): AstronomyResults = dataSource.getAstronomy(
        location,
        date
    )

    override suspend fun getAutocompleteResults(
        /*
            Method to get autocomplete locations data.
        */
        location: String
    ): List<Locations> = dataSource.getAutocompleteResults(
        location
    )
}