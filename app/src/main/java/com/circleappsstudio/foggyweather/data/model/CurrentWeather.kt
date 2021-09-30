package com.circleappsstudio.foggyweather.data.model

data class CurrentWeather(
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val last_updated: String = "",
    val temp_c: Float = 0.0F,
    val feelslike_c: Float = 0.0F,
    val condition: CurrentWeatherCondition
)

data class CurrentWeatherCondition(
    val text: String,
    val icon: String
)

data class CurrentWeatherResults(val current: CurrentWeather, val location: CurrentWeather, val condition: CurrentWeatherCondition)