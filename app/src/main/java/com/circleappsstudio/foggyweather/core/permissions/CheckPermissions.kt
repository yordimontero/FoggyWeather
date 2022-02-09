package com.circleappsstudio.foggyweather.core.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission

fun checkIfLocationPermissionsAreGranted(context: Context): Boolean {
    /*
        Method to check if location permission (GPS) are granted.
    */
    return (checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            &&
            checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
}