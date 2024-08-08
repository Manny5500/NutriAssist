package com.example.projectcontact.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    fun show(context: Context, message: String) {
        toast?.cancel() // Cancel any existing Toast
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}