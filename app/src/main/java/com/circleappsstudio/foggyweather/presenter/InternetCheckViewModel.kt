package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.application.InternetCheck
import com.circleappsstudio.foggyweather.core.Result
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class InternetCheckViewModel : ViewModel() {

    fun checkInternet() = liveData(
        viewModelScope.coroutineContext + Dispatchers.Main
    ) {
        /*
            Method to check if there's internet connection.
        */
        kotlin.runCatching {

            emit(
                Result.Loading()
            )

            InternetCheck.isNetworkAvailable()

        }.onSuccess { isThereInternet ->

            emit(
                Result.Success(isThereInternet)
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

}