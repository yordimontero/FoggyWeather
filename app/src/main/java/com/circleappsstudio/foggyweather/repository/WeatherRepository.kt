package com.circleappsstudio.foggyweather.repository

//import com.circleappsstudio.foggyweather.data.model.CurrentWeatherLocationResults
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults

interface WeatherRepository {

    suspend fun getCurrentWeather(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherResults

    /*suspend fun getCurrentWeatherLocation(
        location: String,
        airQuality: Boolean
    ): CurrentWeatherLocationResults*/

}