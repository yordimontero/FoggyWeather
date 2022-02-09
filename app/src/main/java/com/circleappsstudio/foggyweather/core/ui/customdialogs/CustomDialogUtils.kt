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
        [ENG]: Method that controls InternetCheckDialog UI interactions.
        [ESP]: Método que controla interacciones de UI del InternetCheckDialog.
    */
    fun internetCheckDialogPositiveButtonClicked()
}

fun internetCheckDialog(
    activity: Activity,
    context: Context,
    dialogButtonListener: OnInternetCheckDialogButtonClickListener
) {
    /*
        [ENG]: Method to show a confirmation dialog.
        [ESP]: Método para mostrar un dialog de confirmación.
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