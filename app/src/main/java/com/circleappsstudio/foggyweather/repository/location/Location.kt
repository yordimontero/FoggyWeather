package com.circleappsstudio.foggyweather.repository.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.circleappsstudio.foggyweather.core.checkLocationPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class Location {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context): List<String> {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        var latitude = ""
        var longitude = ""

        val currentLocation = mutableListOf<String>()

        if (checkLocationPermissions(context)) {

            val task = fusedLocationProviderClient.lastLocation

            task.addOnSuccessListener { location ->

                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    currentLocation.add(latitude)
                    currentLocation.add(longitude)
                }

            }.await()

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