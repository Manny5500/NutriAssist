package com.example.projectcontact.util.binding

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.projectcontact.R

@BindingAdapter("backgroundColor")
fun ConstraintLayout.backgroundColor(statusdb : List<String>) {
    val colorString = when {
        statusdb.size > 1 -> "#FF6174"
        statusdb.contains("Normal") -> "#32BA7B"
        else -> "#FF914D"
    }
    this.setBackgroundColor(Color.parseColor(colorString))
}

@BindingAdapter("imageResource")
fun ImageView.imageResource(statusdb : List<String>) {
    val resId = when {
        statusdb.size > 1 -> R.drawable.warning
        statusdb.contains("Normal") -> R.drawable.checked
        else -> R.drawable.information__1_
    }
    this.setImageResource(resId)
}

@BindingAdapter("sizeText")
fun TextView.sizeText(statusdb: List<String>) {
    val size = if (statusdb.size > 1) 25F else 30F
    this.textSize = size
    this.text = statusdb.joinToString(separator = "\n")
}
