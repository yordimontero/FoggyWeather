package com.circleappsstudio.foggyweather.application

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hotchemi.android.rate.AppRate
import javax.inject.Inject

class AppRateUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun initAppRate(): Boolean {
        /*
            Method to initialize AppRate.
         */

        // Production:
        /*AppRate.with(context)
            .setInstallDays(2)
            .setLaunchTimes(4)
            .setDebug(false)
            .monitor()*/

        // Debug:
        AppRate.with(context)
            .setInstallDays(0)
            .setLaunchTimes(3)
            .setDebug(true)
            .monitor()

        return AppRate.with(context).shouldShowRateDialog()

    }
}