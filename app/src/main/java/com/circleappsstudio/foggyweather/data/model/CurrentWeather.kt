package com.circleappsstudio.foggyweather.data.model

// Current Weather:

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

data class CurrentWeatherResults(
    val current: CurrentWeather,
    val location: CurrentWeather,
    val condition: CurrentWeatherCondition
)

// Forecast:

data class ForecastResults(
    val forecast: Forecast
)

data class Forecast(
    val forecastday: List<ForecastDay> = listOf()
)

data class ForecastDay(
    val date: String = "",
    val day: Day
)

data class Day(
    val maxtemp_c: Float = 0.0F,
    val mintemp_c: Float = 0.0F
)