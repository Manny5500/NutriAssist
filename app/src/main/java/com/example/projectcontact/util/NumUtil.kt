package com.example.projectcontact.util

import android.annotation.SuppressLint
import kotlin.math.ceil
import kotlin.math.floor

object NumUtil {
    fun roundToNearestEnding(number: Double): Double {
        return if (number % 1 == 0.0 || number % 1 == 0.5) {
            number
        } else {
            val roundedDown = floor(number)
            val roundedUp = ceil(number)
            if (number - roundedDown < roundedUp - number) {
                roundedDown
            } else {
                roundedUp
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun percentage(number: Float, size: Float) : String{
        return String.format("%.2f", number/size * 100)
    }
}