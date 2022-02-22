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

    fun isRateAppDialogLaunched(): Boolean {
        /*
            Method to check if rate app dialog was launched already or not.
        */
        return globalPreferences.isRateAppDialogLaunched()
    }

    fun setRateAppDialogLaunched() {
        /*
            Method to put true when rate app dialog was launched already.
        */
        globalPreferences.setRateAppDialogLaunched()
    }

    fun wereLocationPermissionsRequestedSingleTime(): Boolean {
        /*
            Method to check if location permission were requested for single time.
        */
        return globalPreferences.wereLocationPermissionsRequestedSingleTime()
    }

    fun setLocationPermissionsRequestedSingleTime() {
        /*
            Method to put true when location permission were requested for single time.
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
            Method to set a last searched location.
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