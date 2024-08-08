package com.example.projectcontact.util.binding

import android.R
import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import com.example.projectcontact.util.Adapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView

object SpinnerBindingAdapters{
    @BindingAdapter("spinnerAdapter")
    @JvmStatic
    fun MaterialAutoCompleteTextView.spinnerAdapter(type: Int){
        val source = Adapter.type(type)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this.context, R.layout.simple_dropdown_item_1line, source)
        this.setAdapter(adapter)
    }

}