package com.circleappsstudio.foggyweather.data.webservice

import com.circleappsstudio.foggyweather.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        /*
            Method to get current weather data from API Rest.
        */
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("aqi")
        airQuality: Boolean
    ): CurrentWeatherResults


    @GET("forecast.json")
    suspend fun getForecast(
        /*
            Method to get forecast data from API Rest.
        */
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("days")
        days: Int,
        @Query("aqi")
        airQuality: Boolean,
        @Query("alerts")
        alerts: Boolean
    ): ForecastResults


    @GET("astronomy.json")
    suspend fun getAstronomy(
        /*
            Method to get astronomy data from API Rest.
        */
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("dt")
        date: String
    ): AstronomyResults


    @GET("search.json")
    suspend fun getAutocompleteResults(
        /*
            Method to get autocomplete locations data from API Rest.
        */
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String
    ): List<Locations>

}