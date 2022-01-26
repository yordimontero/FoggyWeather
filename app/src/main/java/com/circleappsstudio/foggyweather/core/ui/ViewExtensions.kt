package com.circleappsstudio.foggyweather.core.ui

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.show() {
    /*
        [ENG]: Method to show a View item.
        [ESP]: Método para mostrar un item de tipo View.
    */
    this.visibility = View.VISIBLE
}

fun View.hide() {
    /*
        [ENG]: Method to hide a View item.
        [ESP]: Método para ocultar un item de tipo View.
    */
    this.visibility = View.GONE
}

fun Context.showToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    /*
        [ENG]: Method to show a Toast.
        [ESP]: Método para mostrar un Toast.
    */
    Toast.makeText(context, message, duration).show()
}