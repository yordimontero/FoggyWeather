package com.circleappsstudio.foggyweather.core.ui

import android.content.Context
import android.content.res.Configuration
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.circleappsstudio.foggyweather.R

fun changeForecastByHourCardViewColor(
    context: Context,
    splitHour: String,
    currentHour: String,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    when (
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    ) {
        Configuration.UI_MODE_NIGHT_YES -> {

            if (splitHour == currentHour) {

                forecastByHourDarkCardView(
                    context, primaryCardView, secondaryCardView, hour, temperature, grades
                )

            }


        }
        Configuration.UI_MODE_NIGHT_NO ->  {

            if (splitHour == currentHour) {

                forecastByHourLightCardView(
                    context, primaryCardView, secondaryCardView, hour, temperature, grades
                )

            }

        }
        Configuration.UI_MODE_NIGHT_UNDEFINED -> {

            if (splitHour == currentHour) {

                forecastByHourLightCardView(
                    context, primaryCardView, secondaryCardView, hour, temperature, grades
                )

            }

        }
        else -> {

            if (splitHour == currentHour) {

                forecastByHourLightCardView(
                    context, primaryCardView, secondaryCardView, hour, temperature, grades
                )

            }

        }

    }


}

fun forecastByHourLightCardView(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    primaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.dark_blue)
    )

    secondaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.primary_dark_mode)
    )

    hour.setTextColor(
        ContextCompat.getColor(context, R.color.soft_white)
    )

    temperature.setTextColor(
        ContextCompat.getColor(context, R.color.soft_white)
    )

    grades.setTextColor(
        ContextCompat.getColor(context, R.color.soft_white)
    )

}

fun forecastByHourDarkCardView(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    primaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.soft_white)
    )

    secondaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.lavender)
    )

    hour.setTextColor(
        ContextCompat.getColor(context, R.color.primary_dark_mode)
    )

    temperature.setTextColor(
        ContextCompat.getColor(context, R.color.primary_dark_mode)
    )

    grades.setTextColor(
        ContextCompat.getColor(context, R.color.primary_dark_mode)
    )

}