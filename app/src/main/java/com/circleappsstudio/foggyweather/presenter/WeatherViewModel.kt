package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun fetchCurrentWeather(
        location: String,
        airQuality: Boolean
    ) = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {

        kotlin.runCatching {

            emit(Result.Loading())

            repository.getCurrentWeather(
                location,
                airQuality
            )

        }.onSuccess { weather ->
            emit(
                Result.Success(weather)
            )

        }.onFailure { throwable ->
            emit(
                Result.Failure(
                    Exception(
                        throwable.message
                    )
                )
            )
        }

    }

    fun fetchForecast(
        location: String,
        days: Int,
        airQuality: Boolean,
        alerts: Boolean
    ) = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {

        kotlin.runCatching {

            emit(Result.Loading())

            repository.getForecast(
                location,
                days,
                airQuality,
                alerts
            )

        }.onSuccess { forecast ->
            emit(
                Result.Success(forecast)
            )

        }.onFailure { throwable ->
            emit(
                Result.Failure(
                    Exception(
                        throwable.message
                    )
                )
            )
        }

    }

    fun fetchAstronomy(
        location: String,
        date: String
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        kotlin.runCatching {

            emit(Result.Loading())

            repository.getAstronomy(
                location, date
            )

        }.onSuccess { astronomy ->

            emit(Result.Success(astronomy))

        }.onFailure { throwable ->

            emit(
                Result.Failure(
                    Exception(
                        throwable.message
                    )
                )
            )

        }

    }

    fun fetchAutocompleteResults(
        location: String
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        kotlin.runCatching {

            emit(Result.Loading())
            repository.getAutocompleteResults(location)

        }.onSuccess { locations ->

            emit(Result.Success(locations))

        }.onFailure { throwable ->

            emit(
                Result.Failure(
                    Exception(
                        throwable.message
                    )
                )
            )

        }

    }

}