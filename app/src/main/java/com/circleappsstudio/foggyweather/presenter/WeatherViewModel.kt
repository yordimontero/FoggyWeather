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

/*
    @HiltViewModel creates automatically the ViewModel Dependency without create it in AppModule manually.
*/
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun fetchAllWeatherData(
        location: String,
        airQuality: Boolean,
        days: Int,
        alerts: Boolean,
        date: String
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        /*
            Method to fetch all weather data:
            t1 = getCurrentWeather.
            t2 = getForecast.
            t3 = getAstronomy.
        */
        try {

            emit(
                Result.Loading()
            )

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
                Result.Failure(e)
            )

        }

    }

    fun fetchAutocompleteResults(
        location: String
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        /*
            Method to fetch Autocomplete locations data.
        */
        kotlin.runCatching {

            emit(
                Result.Loading()
            )

            repository.getAutocompleteResults(location)

        }.onSuccess { locations ->

            emit(
                Result.Success(locations)
            )

        }.onFailure { throwable ->

            emit(
                Result.Failure(
                    Exception(throwable.message)
                )
            )

        }

    }

}