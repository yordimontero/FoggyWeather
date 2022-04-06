package com.circleappsstudio.foggyweather.core.ui

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
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

fun changeSelectedForecastCardViewColor(
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

fun checkCurrentUIMode(context: Context): String {
    /*
         Method to check current UI mode.
    */
    return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {

        Configuration.UI_MODE_NIGHT_YES -> {
            "Dark Mode"
        }

        Configuration.UI_MODE_NIGHT_NO ->  {
            "Light Mode"
        }

        else -> {
            "Automatic Mode"
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
    when (checkCurrentUIMode(context)) {

        "Dark Mode" -> {
            unselectedForecastCardViewDark(
                context, primaryCardView, secondaryCardView, hour, temperature, grades
            )
        }

        "Light Mode" ->  {
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

fun checkIfDefaultModeIsEnabled(): Boolean {
    /*
         Method to check if default mode UI is enabled.
    */
    return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
}

fun checkIfAutomaticModeIsEnabled(): Boolean {
    /*
         Method to check if automatic mode UI is enabled.
    */
    return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
}

fun setLightMode() {
    /*
         Method to set light mode UI.
    */
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}

fun setDarkMode() {
    /*
         Method to set dark mode UI.
    */
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

fun setAutomaticMode() {
    /*
         Method to set automatic mode UI.
    */
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}

fun addMarquee(textView: TextView) {
    /*
         Method to add marquee to any TextView.
         NOTE: this method only work when TextView width is "wrap_content"
    */
    textView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val pixels = textView.measuredWidth - 1
            val params = textView.layoutParams
            params.width = pixels
            textView.layoutParams = params
            textView.isSelected = true
            textView.ellipsize = TextUtils.TruncateAt.MARQUEE
            textView.isSingleLine = true
            textView.marqueeRepeatLimit = -1
            textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })

}