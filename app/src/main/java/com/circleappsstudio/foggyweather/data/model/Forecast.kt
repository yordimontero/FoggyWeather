package com.circleappsstudio.foggyweather.data.model

data class ForecastResults(
    val forecast: Forecast
)

data class Forecast(
    val forecastday: List<ForecastDay> = listOf()
)

data class ForecastDay(
    val date: String = "",
    val day: Day,
    val hour: List<Hour> = listOf()
)

data class Day(
    val maxtemp_c: Float = 0.0F,
    val mintemp_c: Float = 0.0F,
    val condition: Condition
)

data class Condition(
    val text: String = "",
    val icon: String = ""
)

data class Hour(
    val time: String = "",
    val temp_c: String = "",
    val condition: Condition
)