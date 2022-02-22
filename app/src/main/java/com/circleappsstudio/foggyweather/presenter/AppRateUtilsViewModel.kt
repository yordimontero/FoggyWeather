package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.AppRateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppRateUtilsViewModel @Inject constructor(
    private val appRateUtils: AppRateUtils
): ViewModel() {
    /*
        Method to initialize AppRate.
    */
    fun initAppRate(): Boolean = appRateUtils.initAppRate()
}