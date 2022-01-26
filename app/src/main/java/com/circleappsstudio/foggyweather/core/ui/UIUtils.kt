package com.circleappsstudio.foggyweather.core.ui

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.circleappsstudio.foggyweather.R

fun changeForecastByHourCardViewColor(
    context: Context,
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
            forecastByHourSelectedDarkCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        Configuration.UI_MODE_NIGHT_NO ->  {
            forecastByHourSelectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            forecastByHourSelectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        else -> {
            forecastByHourSelectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

    }


}

fun changeForecastByHourUnselectedCardViewColor(
    context: Context,
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
            forecastByHourUnselectedDarkCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        Configuration.UI_MODE_NIGHT_NO ->  {
            forecastByHourUnselectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            forecastByHourUnselectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        else -> {
            forecastByHourUnselectedLightCardView(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

    }


}

fun forecastByHourSelectedLightCardView(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    primaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.light_dark_blue_primary)
    )

    secondaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.light_dark_blue_secondary)
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

fun forecastByHourSelectedDarkCardView(
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

fun forecastByHourUnselectedLightCardView(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    primaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.white)
    )

    secondaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.lavender)
    )

    hour.setTextColor(
        ContextCompat.getColor(context, R.color.light_mode_text_color)
    )

    temperature.setTextColor(
        ContextCompat.getColor(context, R.color.light_mode_text_color)
    )

    grades.setTextColor(
        ContextCompat.getColor(context, R.color.light_mode_text_color)
    )

}

fun forecastByHourUnselectedDarkCardView(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {

    primaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.primary_dark_mode)
    )

    secondaryCardView.setCardBackgroundColor(
        ContextCompat.getColor(context, R.color.dark_blue)
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

fun Context.hideKeyboard(context: Context, view: View) {
    val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}