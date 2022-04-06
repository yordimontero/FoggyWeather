package com.circleappsstudio.foggyweather.application.preferences

import android.content.Context
import com.circleappsstudio.foggyweather.application.AppConstants.CELSIUS
import com.circleappsstudio.foggyweather.application.AppConstants.GLOBAL_PREFERENCE
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_DID_RATE_APP_DIALOG_IS_LAUNCHED
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_DID_LOCATION_PERMISSION_ARE_REQUESTED_SINGLE_TIME
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_LAST_SEARCHED_LOCATION
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_SELECTED_TEMPERATURE_UNIT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlobalPreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
): GlobalPreferences {

    private val preference = context.getSharedPreferences(GLOBAL_PREFERENCE, Context.MODE_PRIVATE)
    private val editor = preference.edit()

    override fun didRateAppDialogIsLaunched(): Boolean {
        /*
            Method to check if rate app dialog was already launched or not.
        */
        return preference.getBoolean(PREFERENCE_DID_RATE_APP_DIALOG_IS_LAUNCHED, false)
    }

    override fun setRateAppDialogLaunched() {
        /*
            Method to put true when rate app dialog is already launched.
        */
        editor.putBoolean(
            PREFERENCE_DID_RATE_APP_DIALOG_IS_LAUNCHED, true
        ).apply()
    }

    override fun didLocationPermissionsAreRequestedSingleTime(): Boolean {
        /*
            Method to check if location permission were requested for single time.
        */
        return preference.getBoolean(PREFERENCE_DID_LOCATION_PERMISSION_ARE_REQUESTED_SINGLE_TIME, false)
    }

    override fun setLocationPermissionsRequestedSingleTime() {
        /*
            Method to put true when location permission are requested for single time.
        */
        editor.putBoolean(
            PREFERENCE_DID_LOCATION_PERMISSION_ARE_REQUESTED_SINGLE_TIME, true
        ).apply()
    }

    override fun getLastSearchedLocation(): String? {
        /*
            Method to get the last searched location.
        */
        return preference.getString(PREFERENCE_LAST_SEARCHED_LOCATION, null)
    }

    override fun setLastSearchedLocation(location: String) {
        /*
            Method to set the last searched location.
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

    override fun deleteLastSearchedLocation() {
        /*
            Method to delete the last searched location.
        */
        if (getLastSearchedLocation() != null) {
            preference.edit().remove(PREFERENCE_LAST_SEARCHED_LOCATION).apply()
        }
    }

    override fun getTemperatureUnit(): String? {
        /*
            Method to get selected temperature unit.
        */
        if (preference.getString(PREFERENCE_SELECTED_TEMPERATURE_UNIT, null) == null) {
            setTemperatureUnit(CELSIUS)
        }

        return preference.getString(PREFERENCE_SELECTED_TEMPERATURE_UNIT, null)

    }

    override fun setTemperatureUnit(temperatureUnit: String) {
        /*
            Method to set selected temperature unit.
        */
        if (temperatureUnit.isNotEmpty()) {

            editor.putString(
                PREFERENCE_SELECTED_TEMPERATURE_UNIT, temperatureUnit
            ).apply()

        } else {

            editor.putString(
                PREFERENCE_SELECTED_TEMPERATURE_UNIT, ""
            ).apply()

        }

    }

}