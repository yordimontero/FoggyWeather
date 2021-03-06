package com.circleappsstudio.foggyweather.data.model

data class Astro(
    val sunrise: String = "",
    val sunset: String = "",
    val moonrise: String = "",
    val moonset: String = "",
    val moon_phase: String = "",
    val moon_illumination: String = ""
)

data class Astronomy(
    val astro: Astro
)

data class AstronomyResults(
    val astronomy: Astronomy
)