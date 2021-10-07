package com.circleappsstudio.foggyweather.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.core.Result
import com.circleappsstudio.foggyweather.repository.location.LocationRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LocationViewModel(
    private val repository: LocationRepository
): ViewModel() {

    fun fetchLocation(
        context: Context
    ) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        kotlin.runCatching {

            emit(Result.Loading())

            repository.getLocation(context)

        }.onSuccess { location ->
            emit(Result.Success(location))
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

class LocationViewModelFactory(
    val repository: LocationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)
    : T = LocationViewModel(repository) as T

}