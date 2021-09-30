package com.circleappsstudio.foggyweather.repository

//import com.circleappsstudio.foggyweather.data.model.CurrentWeatherLocationResults
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
import com.circleappsstudio.foggyweather.data.remote.WeatherRemoteDataSource

class WeatherRepositoryImpl(
    private val dataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getCurrentWeather(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults = dataSource.getCurrentWeather(location, airQuality)

    /*override suspend fun getCurrentWeatherLocation(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherLocationResults = dataSource.getCurrentWeatherLocation(
        location,
        airQuality
    )*/
}