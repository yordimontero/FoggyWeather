package com.circleappsstudio.foggyweather.core.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat.checkSelfPermission

fun checkIfGPSIsEnabled(context: Context): Boolean {
    /*
        Method to check if GPS is enabled.
    */
    val locationManager: LocationManager = context.getSystemService(
        Context.LOCATION_SERVICE
    ) as LocationManager

    return locationManager.isProviderEnabled(
        LocationManager.GPS_PROVIDER
    )
}

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