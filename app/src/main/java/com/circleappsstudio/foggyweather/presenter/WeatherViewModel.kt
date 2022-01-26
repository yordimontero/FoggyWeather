package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.NTuple3
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

    // Testing:

    fun getAllWeatherInfo(
        location: String,
        airQuality: Boolean,
        days: Int,
        alerts: Boolean,
        date: String
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        try {

            emit(Result.Loading())

            emit(
                Result.Success(
                    NTuple3(
                        t1 = repository.getCurrentWeather(
                            location,
                            airQuality
                        ),
                        t2 = repository.getForecast(
                            location,
                            days,
                            airQuality,
                            alerts
                        ),
                        t3 = repository.getAstronomy(
                            location, date
                        )
                    )
                )
            )

        } catch (e: Exception) {

            emit(
                Result.Failure(
                    e
                )
            )

        }

    }

}