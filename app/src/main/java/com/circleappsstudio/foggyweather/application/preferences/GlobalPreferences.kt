package com.circleappsstudio.foggyweather.application.preferences

interface GlobalPreferences {

    fun didRateAppDialogIsLaunched(): Boolean

    fun setRateAppDialogLaunched()

    fun didLocationPermissionsAreRequestedSingleTime(): Boolean

    fun setLocationPermissionsRequestedSingleTime()

    fun getLastSearchedLocation(): String?

    fun setLastSearchedLocation(location: String)

    fun deleteLastSearchedLocation()

}