package com.circleappsstudio.foggyweather.repository.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.circleappsstudio.foggyweather.core.checkLocationPermissions
import com.circleappsstudio.foggyweather.core.requestLocationPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.*

class Location {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    /*@SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context, activity: Activity): String {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        var latitude: Double
        var longitude: Double

        var city = ""

        if (checkLocationPermissions(context)) {

            val task = fusedLocationProviderClient.lastLocation

            task.addOnSuccessListener { location ->

                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    city = getCity(context, latitude, longitude)
                    Log.wtf("TAG", "Latitude: $latitude, Longitude: $longitude")
                    //city = getCity(context, 40.730610, -73.935242)
                }

            }.await()

        } else {
            requestLocationPermissions(context, activity)
        }

        return city

    }*/

    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context, activity: Activity): List<String> {

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

        } else {
            requestLocationPermissions(context, activity)
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