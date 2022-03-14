package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.apprate.AppRateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
    @HiltViewModel creates automatically the ViewModel Dependency without create it in AppModule manually.
    @Inject constructor(...) injects AppRateUtils Interface.
*/
@HiltViewModel
class AppRateUtilsViewModel @Inject constructor(
    private val appRateUtils: AppRateUtils
): ViewModel() {
    /*
        Method to initialize AppRate.
    */
    fun initAppRate(): Boolean = appRateUtils.initAppRate()
}