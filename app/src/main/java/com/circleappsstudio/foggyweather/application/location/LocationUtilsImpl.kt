package com.circleappsstudio.foggyweather.application.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationUtilsImpl @Inject constructor() : LocationUtils {

    /*@ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(context: Context): List<String> = suspendCancellableCoroutine { cont ->
        /*
            Method to get current location (latitude & longitude) from GPS.
        */

        var latitude = ""
        var longitude = ""
        val currentLocation = mutableListOf<String>()

        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {

            val mLocationRequest = LocationRequest.create()
            mLocationRequest.interval = 60000
            mLocationRequest.fastestInterval = 5000
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val mLocationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    if (locationResult == null) {
                        cont.cancel()
                        return
                    }
                    for (location in locationResult.locations) {
                        if (location != null) {
                            //TODO: UI updates.
                        }
                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)

            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location ->
                //TODO: UI updates.
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()

                Log.wtf("TAG", "latitude: $latitude")
                Log.wtf("TAG", "longitude: $longitude")

                currentLocation.add(latitude)
                currentLocation.add(longitude)

                cont.resume(currentLocation)
            }

        } else {

            val mLocationRequest = com.huawei.hms.location.LocationRequest.create()
            mLocationRequest.interval = 60000
            mLocationRequest.fastestInterval = 5000
            mLocationRequest.priority = com.huawei.hms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

            val mLocationCallback: com.huawei.hms.location.LocationCallback = object : com.huawei.hms.location.LocationCallback() {

                override fun onLocationResult(locationResult: com.huawei.hms.location.LocationResult?) {
                    super.onLocationResult(locationResult)

                    if (locationResult == null) {
                        cont.cancel()
                        return
                    }

                }

            }

            com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)

            val task = com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context).lastLocation

            task.addOnSuccessListener { location ->

                if (location == null) {
                    cont.cancel()
                    return@addOnSuccessListener
                }

                latitude = location.latitude.toString()
                longitude = location.longitude.toString()

                Log.wtf("TAG", "latitude2: $latitude")
                Log.wtf("TAG", "longitude2: $longitude")

                currentLocation.add(latitude)
                currentLocation.add(longitude)

                cont.resume(currentLocation)

            }

        }

    }*/

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override suspend fun requestLocation(context: Context): Unit = withContext(Dispatchers.IO) {
        /*
            Method to request current location from GPS.
        */

        // Check if current device has Google Play Services.
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
        ) {
            /*
                Google Play Services are available.
                Using LocationRequest from Google Mobile Services (gms).
            */

            val locationRequest = LocationRequest.create()
            locationRequest.interval = 20000
            locationRequest.fastestInterval = 1000
            locationRequest.numUpdates = 1
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val locationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    if (locationResult == null) {
                        return
                    }
                    for (location in locationResult.locations) {
                        if (location == null) {
                            return
                        }
                    }
                }
            }

            LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, locationCallback, null)

        } else {
            /*
                Google Play Services are not available.
                Using LocationRequest from Huawei Mobile Services (hms).
            */
            val locationRequest = com.huawei.hms.location.LocationRequest.create()
            locationRequest.interval = 60000
            locationRequest.fastestInterval = 5000
            locationRequest.numUpdates = 1
            locationRequest.priority = com.huawei.hms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

            val mLocationCallback: com.huawei.hms.location.LocationCallback =
                object : com.huawei.hms.location.LocationCallback() {

                    override fun onLocationResult(locationResult: com.huawei.hms.location.LocationResult?) {
                        super.onLocationResult(locationResult)
                        if (locationResult == null) {
                            return
                        }
                        for (location in locationResult.locations) {
                            if (location == null) {
                                return
                            }
                        }

                    }

                }

            com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, mLocationCallback, null)

        }

    }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(context: Context): List<String> =
        suspendCancellableCoroutine { cont ->
            /*
                Method to get current location (latitude & longitude) from GPS.
            */

            val currentLocation = mutableListOf<String>()
            var latitude = ""
            var longitude = ""

            // Check if current device has Google Play Services.
            if (GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
            ) {
                /*
                    Google Play Services are available.
                    Using LocationServices from Google Mobile Services (gms).
                */

                val task = LocationServices.getFusedLocationProviderClient(context).lastLocation

                task.addOnSuccessListener { location ->

                    if (location == null) {
                        cont.cancel()
                        return@addOnSuccessListener
                    }

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    currentLocation.add(latitude)
                    currentLocation.add(longitude)

                    cont.resume(currentLocation)

                }

            } else {
                /*
                    Google Play Services are not available.
                    Using LocationServices from Huawei Mobile Services (hms).
                */
                val task =
                    com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(context).lastLocation

                task.addOnSuccessListener { location ->

                    if (location == null) {
                        cont.cancel()
                        return@addOnSuccessListener
                    }

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    currentLocation.add(latitude)
                    currentLocation.add(longitude)

                    cont.resume(currentLocation)

                }

            }

        }

}