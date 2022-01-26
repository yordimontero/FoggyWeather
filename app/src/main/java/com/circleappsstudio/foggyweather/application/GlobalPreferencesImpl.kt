package com.circleappsstudio.foggyweather.application

import android.content.Context
import com.circleappsstudio.foggyweather.application.AppConstants.GLOBAL_PREFERENCE
import com.circleappsstudio.foggyweather.application.AppConstants.PREFERENCE_SEARCHED_LOCATION
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlobalPreferencesImpl @Inject constructor(@ApplicationContext context: Context) {

    private val preference = context.getSharedPreferences(GLOBAL_PREFERENCE, Context.MODE_PRIVATE)
    private val editor = preference.edit()

    fun getSearchedLocation(): String? {
        return preference.getString(PREFERENCE_SEARCHED_LOCATION, null)
    }

    fun setSearchedLocation(location: String) {

        if (location.isNotEmpty()) {

            editor.putString(
                PREFERENCE_SEARCHED_LOCATION, location
            ).apply()

        } else {

            editor.putString(
                PREFERENCE_SEARCHED_LOCATION, ""
            ).apply()

        }

    }

    fun deleteSearchedLocation() {
        if (getSearchedLocation() != null) {
            preference.edit().remove(PREFERENCE_SEARCHED_LOCATION).apply()
        }
    }

}