package com.circleappsstudio.foggyweather.repository

import com.circleappsstudio.foggyweather.application.AppConstants
import com.circleappsstudio.foggyweather.data.model.CurrentWeatherResults
import com.circleappsstudio.foggyweather.data.model.ForecastResults
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key")
        apiKey: String,
        @Query("q")
        location: String,
        @Query("aqi")
        airQuality: Boolean
    ): CurrentWeatherResults

    @GET("forecast.json")
    suspend fun getForecast(
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

}

object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            ).build().create(WebService::class.java)
    }

}