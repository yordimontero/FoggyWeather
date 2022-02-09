package com.circleappsstudio.foggyweather.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.repository.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
): ViewModel() {

    fun fetchLocation(
        context: Context
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        /*
            Method to fetch current location from GPS.
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