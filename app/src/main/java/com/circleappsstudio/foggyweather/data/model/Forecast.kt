package com.circleappsstudio.foggyweather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
    val text: String = "",
    val icon: String = ""
): Parcelable

@Parcelize
data class Day(
    val maxtemp_c: Float = 0.0F,
    val mintemp_c: Float = 0.0F,
    val condition: Condition
): Parcelable

@Parcelize
data class Hour(
    val time: String = "",
    val temp_c: String = "",
    val condition: Condition,
    val feelslike_c: String = "",
    val chance_of_rain: Int = 0
): Parcelable

@Parcelize
data class ForecastDay(
    val date: String = "",
    val day: Day,
    val hour: List<Hour> = listOf()
): Parcelable

data class Forecast(
    val forecastday: List<ForecastDay> = listOf()
)

data class ForecastResults(
    val forecast: Forecast
)