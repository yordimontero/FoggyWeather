package com.circleappsstudio.foggyweather.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.repository.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception
import javax.inject.Inject

/*
    @HiltViewModel creates automatically the ViewModel Dependency without create it in AppModule manually.
    @Inject constructor(...) injects LocationRepository Interface.
*/
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
): ViewModel() {

    @ExperimentalCoroutinesApi
    fun requestLocation(
        context: Context
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        /*
            Method to request current location from GPS.
        */
        kotlin.runCatching {

            emit(
                Result.Loading()
            )

            repository.requestLocation(context)

        }.onSuccess {

            emit(
                Result.Success(it)
            )

        }.onFailure { throwable ->

            emit(
                Result.Failure(
                    Exception(throwable.message)
                )
            )

        }

    }

    @ExperimentalCoroutinesApi
    fun fetchLocation(
        context: Context
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        /*
            Method to fetch current location (latitude & longitude) from GPS.
        */
        kotlin.runCatching {

            emit(
                Result.Loading()
            )

            repository.getLocation(context)

        }.onSuccess { location ->

            emit(
                Result.Success(location)
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