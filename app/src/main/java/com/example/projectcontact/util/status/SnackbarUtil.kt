package com.example.projectcontact.util.status

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {
    private var snackbar: Snackbar? = null

    fun show(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        snackbar?.dismiss() // Dismiss any existing Snackbar
        snackbar = Snackbar.make(view, message, duration)
        snackbar?.show()
    }
}