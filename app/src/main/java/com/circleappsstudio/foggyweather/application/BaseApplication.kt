package com.circleappsstudio.foggyweather.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
    BaseApplication class for Dagger Hilt.
*/
@HiltAndroidApp
class BaseApplication : Application()