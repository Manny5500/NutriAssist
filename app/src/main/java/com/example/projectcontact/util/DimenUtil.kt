package com.example.projectcontact.util

import android.content.Context
import android.util.TypedValue

object DimenUtil {
    fun toDp(size: Float, context: Context):Int{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 350f, context.resources.displayMetrics
        ).toInt()
    }
}