package com.circleappsstudio.foggyweather.application

import android.content.Context
import com.circleappsstudio.foggyweather.application.AppConstants.GLOBAL_PREFERENCE
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_WERE_LOCATION_PERMISSION_REQUESTED_SINGLE_TIME
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_LAST_SEARCHED_LOCATION
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlobalPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val preference = context.getSharedPreferences(GLOBAL_PREFERENCE, Context.MODE_PRIVATE)
    private val editor = preference.edit()

    fun wereLocationPermissionsRequestedSingleTime(): Boolean {
        /*
            Method to check if location permission were requested for single time.
        */
        return preference.getBoolean(PREFERENCE_WERE_LOCATION_PERMISSION_REQUESTED_SINGLE_TIME, false)
    }

    fun setLocationPermissionsRequestedSingleTime() {
        /*
            Method to put true when location permission were requested for single time.
        */
        editor.putBoolean(
            PREFERENCE_WERE_LOCATION_PERMISSION_REQUESTED_SINGLE_TIME, true
        ).apply()
    }

    fun getLastSearchedLocation(): String? {
        /*
            Method to get the last searched location.
        */
        return preference.getString(PREFERENCE_LAST_SEARCHED_LOCATION, null)
    }

    fun setLastSearchedLocation(location: String) {
        /*
            Method to set a last searched location.
        */
        if (location.isNotEmpty()) {

            editor.putString(
                PREFERENCE_LAST_SEARCHED_LOCATION, location
            ).apply()

        } else {

            editor.putString(
                PREFERENCE_LAST_SEARCHED_LOCATION, ""
            ).apply()

        }

    }

    fun deleteLastSearchedLocation() {
        /*
            Method to delete the last searched location.
        */
        if (getLastSearchedLocation() != null) {
            preference.edit().remove(PREFERENCE_LAST_SEARCHED_LOCATION).apply()
        }
    }

}