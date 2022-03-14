package com.circleappsstudio.foggyweather.core.ui

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.circleappsstudio.foggyweather.R

fun View.show() {
    /*
        Method to show a View item.
    */
    this.visibility = View.VISIBLE
}

fun View.hide() {
    /*
        Method to hide a View item.
    */
    this.visibility = View.GONE
}

fun Context.showToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    /*
        Method to show a Toast.
    */
    Toast.makeText(context, message, duration).show()
}

fun Context.hideKeyboard(context: Context, view: View) {
    /*
        Method to hide keyboard.
    */
    val inputMethodManager: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE)
            as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun changeForecastCardViewColor(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to change card aspect based if it's selected or unselected card.
    */
    when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {

        Configuration.UI_MODE_NIGHT_YES -> {
            selectedForecastCardViewDark(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        Configuration.UI_MODE_NIGHT_NO ->  {
            selectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }
        Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            selectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        else -> {
            selectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

    }

}

fun changeUnselectedForecastCardViewColor(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to change all unselected cards aspect based if Light Mode or Dark Mode is enabled.
    */
    when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {

        Configuration.UI_MODE_NIGHT_YES -> {
            unselectedForecastCardViewDark(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        Configuration.UI_MODE_NIGHT_NO ->  {
            unselectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            unselectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        else -> {
            unselectedForecastCardViewLight(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

    }

}

fun selectedForecastCardViewLight(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to gives style to selected card when Light Mode is enabled.
    */
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

fun selectedForecastCardViewDark(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to gives style to selected card when Dark Mode is enabled.
    */
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

fun unselectedForecastCardViewLight(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to gives style to unselected cards when Light Mode is enabled.
    */
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

fun unselectedForecastCardViewDark(
    context: Context,
    primaryCardView: CardView,
    secondaryCardView: CardView,
    hour: TextView,
    temperature: TextView,
    grades: TextView
) {
    /*
        Method to gives style to unselected cards when Dark Mode is enabled.
    */
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