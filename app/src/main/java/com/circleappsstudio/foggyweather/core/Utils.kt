package com.circleappsstudio.foggyweather.core

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun checkHourFormat(context: Context): Boolean = DateFormat.is24HourFormat(context)

fun splitDate(date: String): String {
    val splitDate = date.split(" ")
    return splitDate[1]
}

fun splitHour(date: String, position: Int): String {
    val splitHour = date.split(":")
    return splitHour[position]
}

fun getCurrentHour(context: Context): String {

    val formatter12h = SimpleDateFormat("hh:mm a", Locale.US)
    val formatter24h = SimpleDateFormat("HH:mm", Locale.US)
    val currentHour = Calendar.getInstance().time

    return if (DateFormat.is24HourFormat(context)) {
        formatter24h.format(currentHour)
    } else {
        formatter12h.format(currentHour)
    }

}

fun getCurrentForecastCard(currentHour: String, context: Context): Int {

    var value = 0

    if (DateFormat.is24HourFormat(context)) {

        when (currentHour.toInt()) {
            0 -> value = 0
            1 -> value = 1
            2 -> value = 2
            3 -> value = 3
            4 -> value = 4
            5 -> value = 5
            6 -> value = 6
            7 -> value = 7
            8 -> value = 8
            9 -> value = 9
            10 -> value = 10
            11 -> value = 11
            12 -> value = 12
            13 -> value = 13
            14 -> value = 14
            15 -> value = 15
            16 -> value = 16
            17 -> value = 17
            18 -> value = 18
            19 -> value = 19
            20 -> value = 20
            21 -> value = 21
            22 -> value = 22
            23 -> value = 23
            24 -> value = 24
            else -> value = 0
        }

    } else {

        // Bug.

        if (currentHour.contains("1 AM")) {
            value = 1
        }
        if (currentHour.contains("2 AM")) {
            value = 2
        }
        if (currentHour.contains("3 AM")) {
            value = 3
        }
        if (currentHour.contains("4 AM")) {
            value = 4
        }
        if (currentHour.contains("5 AM")) {
            value = 5
        }
        if (currentHour.contains("6 AM")) {
            value = 6
        }
        if (currentHour.contains("7 AM")) {
            value = 7
        }
        if (currentHour.contains("8 AM")) {
            value = 8
        }
        if (currentHour.contains("9 AM")) {
            value = 9
        }
        if (currentHour.contains("10 AM")) {
            value = 10
        }
        if (currentHour.contains("11 AM")) {
            value = 11
        }
        if (currentHour.contains("12 PM")) {
            value = 12
        }
        if (currentHour.contains("1 PM")) {
            value = 13
        }
        if (currentHour.contains("2 PM")) {
            value = 14
        }
        if (currentHour.contains("3 PM")) {
            value = 15
        }
        if (currentHour.contains("4 PM")) {
            value = 16
        }
        if (currentHour.contains("5 PM")) {
            value = 17
        }
        if (currentHour.contains("6 PM")) {
            value = 18
        }
        if (currentHour.contains("7 PM")) {
            value = 19
        }
        if (currentHour.contains("8 PM")) {
            value = 20
        }
        if (currentHour.contains("9 PM")) {
            value = 21
        }
        if (currentHour.contains("10 PM")) {
            value = 22
        }
        if (currentHour.contains("11 PM")) {
            value = 23
        }
        if (currentHour.contains("12 AM")) {
            value = 24
        }

    }

    return value

}