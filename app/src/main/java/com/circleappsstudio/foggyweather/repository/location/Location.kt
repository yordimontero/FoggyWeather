package com.circleappsstudio.foggyweather.repository.location

import android.annotation.SuppressLint
import android.content.Context
import com.circleappsstudio.foggyweather.core.permissions.checkLocationPermissions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class Location @Inject constructor() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var huaweiFusedLocationProviderClient: com.huawei.hms.location.FusedLocationProviderClient

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context): List<String> {

        var latitude = ""
        var longitude = ""

        val currentLocation = mutableListOf<String>()

        withContext(Dispatchers.IO) {

            suspendCancellableCoroutine<MutableList<String>> { coroutine ->

                if (GoogleApiAvailability.getInstance()
                        .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
                ) {
                    //Google Play services are available

                    fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context)

                    if (checkLocationPermissions(context)) {

                        val task = fusedLocationProviderClient.lastLocation

                        task.addOnSuccessListener { location ->

                            if (location != null) {
                                latitude = location.latitude.toString()
                                longitude = location.longitude.toString()

                                currentLocation.add(latitude)
                                currentLocation.add(longitude)

                                coroutine.resume(currentLocation)

                            } else {
                                coroutine.cancel()
                            }

                        }

                    }

                } else {
                    //Google Play services are not available on this device

                    huaweiFusedLocationProviderClient =
                        com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(
                            context
                        )

                    if (checkLocationPermissions(context)) {

                        val task = huaweiFusedLocationProviderClient.lastLocation

                        task.addOnSuccessListener { location ->

                            if (location != null) {
                                latitude = location.latitude.toString()
                                longitude = location.longitude.toString()

                                currentLocation.add(latitude)
                                currentLocation.add(longitude)

                                coroutine.resume(currentLocation)

                            } else {
                                coroutine.cancel()
                            }

                        }

                    }

                }

            }

        }

        return currentLocation

    }

    /*fun getCity(context: Context, latitude: Double, longitude: Double): String {

        var cityName = ""

        val geoCoder = Geocoder(context, Locale.getDefault())

        val adress: List<Address>

        try {

            adress = geoCoder.getFromLocation(latitude, longitude, 1)
            val subAdminArea = adress[0].subAdminArea

            cityName = if (!subAdminArea.isNullOrEmpty()) {
                subAdminArea
            } else {
                ""
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return cityName

    }*/

}