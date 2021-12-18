package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.circleappsstudio.foggyweather.application.InternetCheck
import com.circleappsstudio.foggyweather.core.Result
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class InternetCheckViewModel : ViewModel() {

    fun checkInternet() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {

        try {

            emit(Result.Loading())
            emit(
                Result.Success(InternetCheck.isNetworkAvailable())
            )

        } catch (e: Exception) {

            emit(
                Result.Failure(e)
            )

        }

    }

}