package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.GlobalPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalPreferencesViewModel @Inject constructor(
    private val globalPreferences: GlobalPreferences
) : ViewModel() {

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