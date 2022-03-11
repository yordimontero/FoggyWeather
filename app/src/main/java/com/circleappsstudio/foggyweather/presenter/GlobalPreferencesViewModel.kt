package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.GlobalPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
    @HiltViewModel creates automatically the ViewModel Dependency without create it in AppModule manually.
*/
@HiltViewModel
class GlobalPreferencesViewModel @Inject constructor(
    private val globalPreferences: GlobalPreferences
) : ViewModel() {

    fun didRateAppDialogIsLaunched(): Boolean {
        /*
            Method to check if rate app dialog was already launched or not.
        */
        return globalPreferences.didRateAppDialogIsLaunched()
    }

    fun setRateAppDialogLaunched() {
        /*
            Method to put true when rate app dialog is already launched.
        */
        globalPreferences.setRateAppDialogLaunched()
    }

    fun didLocationPermissionsAreRequestedSingleTime(): Boolean {
        /*
            Method to check if location permission were requested for single time.
        */
        return globalPreferences.didLocationPermissionsAreRequestedSingleTime()
    }

    fun setLocationPermissionsRequestedSingleTime() {
        /*
            Method to put true when location permission are requested for single time.
        */
        globalPreferences.setLocationPermissionsRequestedSingleTime()
    }

    fun getLastSearchedLocation(): String? {
        /*
            Method to get the last searched location.
        */
        return globalPreferences.getLastSearchedLocation()
    }

    fun setLastSearchedLocation(location: String) {
        /*
            Method to set the last searched location.
        */
        globalPreferences.setLastSearchedLocation(location)
    }

    fun deleteLastSearchedLocation() {
        /*
            Method to delete the last searched location.
        */
        globalPreferences.deleteLastSearchedLocation()
    }

}