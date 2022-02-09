package com.circleappsstudio.foggyweather.core.time

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun splitDate(date: String): String {
    /*
        Method to split date of hour from API field. Example: 2022-02-09 19:45
    */
    val splitDate = date.split(" ")
    return splitDate[1]
}

fun splitHour(hour: String, position: Int): String {
    /*
        Method to split fetched hour from API in hours and minutes. Example: 19:45
    */
    val splitHour = hour.split(":")
    return splitHour[position]
}

fun getCurrentHourFormatted(context: Context): String {
    /*
        Method to get current hour and format it in 12 h or 24 h.
    */
    val formatter12h = SimpleDateFormat("hh:mm a", Locale.US)
    val formatter24h = SimpleDateFormat("HH:mm", Locale.US)
    val currentHour = Calendar.getInstance().time

    return if (DateFormat.is24HourFormat(context)) {
        formatter24h.format(currentHour)
    } else {
        formatter12h.format(currentHour)
    }

}

fun formatHour(hour: String, minute: String, context: Context): String {
    /*
        Method to get an hour an format it in 12 h or 24 h.
    */
    val formatter12h = SimpleDateFormat("hh:mm a", Locale.US)
    val formatter24h = SimpleDateFormat("HH:mm", Locale.US)
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
    calendar.set(Calendar.MINUTE, minute.toInt())

    val hourParsed: String = if (DateFormat.is24HourFormat(context)) {
        formatter24h.format(calendar.time)
    } else {
        formatter12h.format(calendar.time)
    }

    return hourParsed

}

fun getCurrentForecastCard(currentHour: String, context: Context): Int {
    /*
        Method to get the current card in Forecast RecyclerView based on current hour.
    */
    var value = 0

    val splitHour24 = splitHour(currentHour, 0)

    if (DateFormat.is24HourFormat(context)) {

        when (splitHour24.toInt()) {
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

        if (currentHour.startsWith("01") && currentHour.endsWith("AM")) {
            value = 1
        }
        if (currentHour.startsWith("02") && currentHour.endsWith("AM")) {
            value = 2
        }
        if (currentHour.startsWith("03") && currentHour.endsWith("AM")) {
            value = 3
        }
        if (currentHour.startsWith("04") && currentHour.endsWith("AM")) {
            value = 4
        }
        if (currentHour.startsWith("05") && currentHour.endsWith("AM")) {
            value = 5
        }
        if (currentHour.startsWith("06") && currentHour.endsWith("AM")) {
            value = 6
        }
        if (currentHour.startsWith("07") && currentHour.endsWith("AM")) {
            value = 7
        }
        if (currentHour.startsWith("08") && currentHour.endsWith("AM")) {
            value = 8
        }
        if (currentHour.startsWith("09") && currentHour.endsWith("AM")) {
            value = 9
        }
        if (currentHour.startsWith("10") && currentHour.endsWith("AM")) {
            value = 10
        }
        if (currentHour.startsWith("11") && currentHour.endsWith("AM")) {
            value = 11
        }
        if (currentHour.startsWith("12") && currentHour.endsWith("PM")) {
            value = 12
        }
        if (currentHour.startsWith("01") && currentHour.endsWith("PM")) {
            value = 13
        }
        if (currentHour.startsWith("02") && currentHour.endsWith("PM")) {
            value = 14
        }
        if (currentHour.startsWith("03") && currentHour.endsWith("PM")) {
            value = 15
        }
        if (currentHour.startsWith("04") && currentHour.endsWith("PM")) {
            value = 16
        }
        if (currentHour.startsWith("05") && currentHour.endsWith("PM")) {
            value = 17
        }
        if (currentHour.startsWith("06") && currentHour.endsWith("PM")) {
            value = 18
        }
        if (currentHour.startsWith("07") && currentHour.endsWith("PM")) {
            value = 19
        }
        if (currentHour.startsWith("08") && currentHour.endsWith("PM")) {
            value = 20
        }
        if (currentHour.startsWith("09") && currentHour.endsWith("PM")) {
            value = 21
        }
        if (currentHour.startsWith("10") && currentHour.endsWith("PM")) {
            value = 22
        }
        if (currentHour.startsWith("11") && currentHour.endsWith("PM")) {
            value = 23
        }
        if (currentHour.startsWith("12") && currentHour.endsWith("AM")) {
            value = 24
        }

    }

    return value

}

fun formatDate(date: Date): String {
    /*
        Method to format a date in year-month-day.
    */
    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd", Locale.ENGLISH
    )

    return dateFormat.format(date)

}

fun getCurrentDate(): String {
    /*
        Method to get current date.
    */
    return formatDate(Calendar.getInstance().time)
}

fun getDateWithMonthName(date: Date): String {
    /*
        Method to get current date with month name. Example: Wed, 9 Feb.
    */
    val dateFormat = SimpleDateFormat(
        "EEE, d MMM", Locale.ENGLISH
    )

    return dateFormat.format(date)
}

