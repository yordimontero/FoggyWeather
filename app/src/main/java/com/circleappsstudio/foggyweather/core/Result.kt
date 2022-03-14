package com.circleappsstudio.foggyweather.core

import java.lang.Exception

// Each method of Result() class return itself (: Resource<T>())
sealed class Result<out T> {

    // Result.Loading()
    class Loading<out T>: Result<T>()

    // Result.Success()
    data class Success<out T>(val data: T): Result<T>()

    // Result.Failure()
    data class Failure(val exception: Exception): Result<Nothing>()

}
