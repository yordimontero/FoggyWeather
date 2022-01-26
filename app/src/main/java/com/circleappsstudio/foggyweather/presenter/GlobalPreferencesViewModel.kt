package com.circleappsstudio.foggyweather.presenter

import androidx.lifecycle.ViewModel
import com.circleappsstudio.foggyweather.application.GlobalPreferencesImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalPreferencesViewModel @Inject constructor(
    private val globalPreferences: GlobalPreferencesImpl
) : ViewModel() {

    fun getSearchedLocation(): String? {
        return globalPreferences.getSearchedLocation()
    }

    fun setSearchedLocation(location: String) {
        globalPreferences.setSearchedLocation(location)
    }

    fun deleteSearchedLocation() {
        globalPreferences.deleteSearchedLocation()
    }

}