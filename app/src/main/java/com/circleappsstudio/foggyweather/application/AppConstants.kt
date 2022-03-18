package com.circleappsstudio.foggyweather.application

object AppConstants {
    /*
        Global app constants.
    */
    const val API_KEY: String = "8f4e711b603745daa94222643210605"
    const val BASE_URL: String = "https://api.weatherapi.com/v1/"
    const val BASE_IMAGE_URL: String = "https:"
    const val LOCATION_REQUEST_CODE: Int = 101
    const val FORECAST_LIST: String = "forecastList"
    const val FORECAST_LIST_POSITION: String = "forecastListPosition"
    const val FORECAST_DATE: String = "forecastDate"
    const val GLOBAL_PREFERENCE = "FoggyWeatherSharedPreferences"
    const val PREFERENCE_DID_RATE_APP_DIALOG_IS_LAUNCHED = "DidRateAppDialogIsLaunched"
    const val PREFERENCE_LAST_SEARCHED_LOCATION = "LastSearchedLocation"
    const val PREFERENCE_DID_LOCATION_PERMISSION_ARE_REQUESTED_SINGLE_TIME = "DidLocationPermissionsAreRequestedSingleTime"
}