package com.example.projectcontact.util.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.projectcontact.util.AgeUtil
import com.example.projectcontact.util.DateUtil

object DateBindingAdapters {
    @SuppressLint("SetTextI18n")
    @BindingAdapter("toAgeinYears")
    @JvmStatic
    fun setAgeDisplay(textView: TextView, dateString: String?){
        when (dateString) {
            null -> textView.text = "Not set"
            "" -> textView.text = "Not set"
            else -> {
                val age = AgeUtil.yearsBetweenToday(DateUtil.toDateFormate(dateString))
                textView.text = age.toString()
            }
        }
    }
}