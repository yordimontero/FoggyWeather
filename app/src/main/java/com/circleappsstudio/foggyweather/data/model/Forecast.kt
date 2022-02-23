package com.circleappsstudio.foggyweather.data.model

data class Condition(
    val text: String = "",
    val icon: String = ""
)

data class Day(
    val maxtemp_c: Float = 0.0F,
    val mintemp_c: Float = 0.0F,
    val condition: Condition
)

data class Hour(
    val time: String = "",
    val temp_c: String = "",
    val condition: Condition,
    val feelslike_c: String = ""
)

data class ForecastDay(
    val date: String = "",
    val day: Day,
    val hour: List<Hour> = listOf()
)

data class Forecast(
    val forecastday: List<ForecastDay> = listOf()
)

data class ForecastResults(
    val forecast: Forecast
)