package com.circleappsstudio.foggyweather.application.location

import android.annotation.SuppressLint
import android.content.Context
import com.circleappsstudio.foggyweather.core.permissions.checkIfGPSIsEnabled
import com.circleappsstudio.foggyweather.core.permissions.checkIfLocationPermissionsAreGranted
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationUtilsImpl @Inject constructor(): LocationUtils {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var huaweiFusedLocationProviderClient: com.huawei.hms.location.FusedLocationProviderClient

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(context: Context): List<String> = suspendCancellableCoroutine { cont ->
        /*
            Method to get current location (latitude & longitude) from GPS.
        */

        // Checking if GPS is enabled.
        if (checkIfGPSIsEnabled(context)) {
            // GPS is enabled.

            var latitude = ""
            var longitude = ""

            val currentLocation = mutableListOf<String>()

            // Check if current device has Google Play Services.
            if (GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
                // Google Play Services are available.

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)

                if (checkIfLocationPermissionsAreGranted(context)) {

                    val task = fusedLocationProviderClient.lastLocation

                    task.addOnSuccessListener { location ->

                        if (location != null) {

                            latitude = location.latitude.toString()
                            longitude = location.longitude.toString()

                            currentLocation.add(latitude)
                            currentLocation.add(longitude)

                            cont.resume(currentLocation)

                        } else {
                            cont.cancel()
                        }

                    }

                }

            } else {
                // Google Play Services are not available.

                huaweiFusedLocationProviderClient =
                    com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(
                        context
                    )

                if (checkIfLocationPermissionsAreGranted(context)) {

                    val task = huaweiFusedLocationProviderClient.lastLocation

                    task.addOnSuccessListener { location ->

                        if (location != null) {

                            latitude = location.latitude.toString()
                            longitude = location.longitude.toString()

                            currentLocation.add(latitude)
                            currentLocation.add(longitude)

                            cont.resume(currentLocation)

                        } else {
                            cont.cancel()
                        }

                    }

                }

            }

        } else {
            // GPS is disabled.
            cont.cancel()
        }

    }

}