package com.example.projectcontact.util.binding

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.google.android.material.textfield.TextInputEditText

object TextBindingAdapters {
    @BindingAdapter("android:text")
    @JvmStatic
    fun bindDouble(editText: TextInputEditText, value: Double?) {
        if (value != null && editText.text.toString() != value.toString()) {
            editText.setText(value.toString())
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    @JvmStatic
    fun getDouble(editText: TextInputEditText): Double {
        return editText.text.toString().toDoubleOrNull() ?: 0.0
    }

    @BindingAdapter("concatText", "concatText2")
    @JvmStatic
    fun setConcatenatedText(textView: TextView, text1: String?, text2: String?) {
        val concatenatedText = (text1 ?: "") + " " + (text2 ?: "")
        textView.text = concatenatedText
    }


}