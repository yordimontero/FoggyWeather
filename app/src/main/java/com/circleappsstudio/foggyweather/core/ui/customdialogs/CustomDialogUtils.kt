package com.circleappsstudio.foggyweather.core.ui.customdialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.circleappsstudio.foggyweather.databinding.CustomAppRateDialogBinding
import com.circleappsstudio.foggyweather.databinding.CustomInternetCheckDialogBinding

interface OnInternetCheckDialogClickListener {
    /*
        Method that controls internetCheckDialog UI interactions.
    */
    fun internetCheckDialogPositiveButtonClicked()
}

fun internetCheckDialog(
    activity: Activity,
    context: Context,
    dialogListener: OnInternetCheckDialogClickListener
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
        dialogListener.internetCheckDialogPositiveButtonClicked()
        dialogInstance.dismiss()
    }

}

interface OnConfirmationDialogClickListener {
    /*
        Method that controls confirmation dialogs UI interactions.
    */
    fun confirmationDialogPositiveButtonClicked()
}

fun appRateDialog(
    activity: Activity,
    context: Context,
    dialogListener: OnConfirmationDialogClickListener
) {
    /*
        Method to show a App Rate dialog.
    */

    // Inflate the dialog as custom view:
    val binding = CustomAppRateDialogBinding.inflate(LayoutInflater.from(context))

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
    binding.btnConfirm.setOnClickListener {
        // Close dialog:
        dialogListener.confirmationDialogPositiveButtonClicked()
        dialogInstance.dismiss()
    }

    // Set Listener:
    binding.btnCancel.setOnClickListener {
        // Close dialog:
        dialogInstance.dismiss()
    }

}