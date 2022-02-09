package com.circleappsstudio.foggyweather.core.ui.customdialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.circleappsstudio.foggyweather.databinding.CustomInternetCheckDialogBinding

interface OnInternetCheckDialogButtonClickListener {
    /*
        Method that controls InternetCheckDialog UI interactions.
    */
    fun internetCheckDialogPositiveButtonClicked()
}

fun internetCheckDialog(
    activity: Activity,
    context: Context,
    dialogButtonListener: OnInternetCheckDialogButtonClickListener
) {
    /*
        Method to show a dialog when there's not internet connection.
    */

    // Inflate the dialog as custom view:
    val binding = CustomInternetCheckDialogBinding.inflate(LayoutInflater.from(context))

    // AlertDialogBuilder:
    val dialogBuilder = AlertDialog.Builder(activity).setView(binding.root)
    dialogBuilder.setCancelable(false)

    // Show dialog:
    val dialogInstance = dialogBuilder.show()

    // Setting dialog with transparent background:
    dialogInstance.window?.setBackgroundDrawable(
        ColorDrawable(Color.TRANSPARENT)
    )

    // Set Listener:
    binding.btnTryAgain.setOnClickListener {
        // Close dialog:
        dialogInstance.dismiss()
        dialogButtonListener.internetCheckDialogPositiveButtonClicked()
    }

}