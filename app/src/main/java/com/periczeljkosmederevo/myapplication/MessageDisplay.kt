package com.periczeljkosmederevo.myapplication

import android.app.AlertDialog
import android.content.Context

typealias DialogAction = (AlertDialog) -> Unit

object MessageDisplay {

    fun showGenericDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String,
        onPositiveClick: DialogAction,
        negativeButtonText: String? = null,
        onNegativeClick: DialogAction? = null
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)

        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveClick(dialog as AlertDialog)
        }

        if (negativeButtonText != null && onNegativeClick != null) {
            builder.setNegativeButton(negativeButtonText) { dialog, _ ->
                onNegativeClick(dialog as AlertDialog)
            }
        }

        builder.show()
    }
}