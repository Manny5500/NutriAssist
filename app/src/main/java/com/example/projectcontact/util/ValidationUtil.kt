package com.example.projectcontact.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


object ValidationUtil {

    private const val GMAILPATTERN = "^[a-zA-Z0-9._-]+@gmail\\.com$"
    private const val YAHOOMAILPATTERN = "^[a-zA-Z0-9._-]+@yahoo\\.com$"
    private const val NAMEPATTERN = "[a-zA-Z]+([.\\s]?[a-zA-Z]+)?([\\s]?[a-zA-Z]+)?"
    private const val CONTACTPATTERN = "^09\\d{9}$"


    fun isErrorEmpty(vararg string: String?):Boolean{
        return string.all{it == null}
    }

    fun String.isNameValid(): Boolean {
        return this.matches(NAMEPATTERN.toRegex())
    }

    fun String.isGmailValid(): Boolean{
        return this.matches(GMAILPATTERN.toRegex())
    }

    fun String.isContactNoValid(): Boolean{
        return this.matches(CONTACTPATTERN.toRegex())
    }

    fun String.checkedDaysDiff(): Boolean{
        return isValidDaysDiff(this)
    }

    @SuppressLint("SimpleDateFormat")
    fun isValidDaysDiff(birthDate : String) : Boolean{
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date: Date? = dateFormat.parse(birthDate)
        val daysDiff: Long = AgeUtil.daysBetweenTodayMill(date)
        return daysDiff >= 0
    }

}