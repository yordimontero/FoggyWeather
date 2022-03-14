package com.circleappsstudio.foggyweather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastByDay(
    val forecastListDay: List<ForecastDay>,
    val position: Int,
    val date: String
): Parcelable