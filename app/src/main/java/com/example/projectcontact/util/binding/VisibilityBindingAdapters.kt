package com.example.projectcontact.util.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object VisibilityBindingAdapters {

    @BindingAdapter("isVisibleRequest")
    @JvmStatic
    fun setVisibility(view: View, text1: String?) {
        when(text1){
            "true" -> view.visibility = View.GONE
            else -> view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("isVisibleUndo")
    @JvmStatic
    fun setVisibilityUndo(view: View, text1: String?) {
        when(text1){
            "true" -> view.visibility = View.VISIBLE
            else -> view.visibility = View.GONE
        }
    }

}