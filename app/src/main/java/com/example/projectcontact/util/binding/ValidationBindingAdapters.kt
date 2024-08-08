package com.example.projectcontact.util.binding

import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object ValidationBindingAdapters {
    @BindingAdapter("clearErrorOnTextChange")
    @JvmStatic
    fun setClearErrorOnTextChange(editText: TextInputEditText, textInputLayout: TextInputLayout?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textInputLayout?.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    @BindingAdapter("clearErrorOnTextChange")
    @JvmStatic
    fun setClearErrorOnTextChange(editText: AutoCompleteTextView, textInputLayout: TextInputLayout?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textInputLayout?.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}