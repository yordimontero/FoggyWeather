package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class WeatherViewModel(
    private val repository: WeatherRepository
): ViewModel() {

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

    /*fun fetchCurrentWeatherLocation(
        location: String,
        airQuality: Boolean
    ) = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {

        kotlin.runCatching {

            emit(Result.Loading())

            repository.getCurrentWeatherLocation(
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

    }*/

}

class WeatherViewModelFactory(
    private val repository: WeatherRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
    : T = WeatherViewModel(repository) as T
}