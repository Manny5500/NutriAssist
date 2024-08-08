package com.example.projectcontact.util

import com.example.projectcontact.model.Child

object InitObjectUtil {
    fun child():Child{
        return Child("","", "", emptyList(),
            emptyList(), "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", 0.0, 0.0,
            "")
    }
}