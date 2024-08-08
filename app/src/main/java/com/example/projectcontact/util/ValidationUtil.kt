package com.example.projectcontact.util

import android.annotation.SuppressLint
import android.util.Log
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.Parent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


object ValidationUtil {

    private const val GMAILPATTERN = "^[a-zA-Z0-9._-]+@gmail\\.com$"
    private const val YAHOOMAILPATTERN = "^[a-zA-Z0-9._-]+@yahoo\\.com$"
    private const val NAMEPATTERN = "[a-zA-Z]+([.\\s]?[a-zA-Z]+)?([\\s]?[a-zA-Z]+)?"
    private const val CONTACTPATTERN = "^09\\d{9}$"

    @SuppressLint("SimpleDateFormat")
    fun updateChild(child: Child, monthdiff: Int): Boolean {
        if(isEmptyString(
            child.childFirstName, child.childMiddleName, child.childLastName,
                child.birthDate, child.expectedDate, child.sex
        )){
            return false
        }
        if(isZeroDouble(
            child.height, child.weight
        )
        ){
            return false
        }

        //String firstnamePattern = "[a-zA-Z]+([.\\s]?[a-zA-Z]+)*";
        if (!isValidName(
            child.childFirstName, child.childMiddleName, child.childLastName
        )
        ){
            return false
        }

        if (monthdiff < 0 || monthdiff > 59) {
            return false
        }

        if(!isValidDaysDiff(child.birthDate))
            return false

        return true
    }


    fun addParent(parent: Parent) : Boolean{

        if(isEmptyString(
            parent.parentFirstName, parent.parentMiddleName,
            parent.parentLastName, parent.gmail
        )){
            Log.d("MyApp", "Empty parent")
            return false
        }

        if(!isValidName(
            parent.parentFirstName,
            parent.parentMiddleName,
            parent.parentLastName
        )){
            Log.d("MyApp", "Invalid parent")
            return false
        }

        if (!parent.gmail.matches(GMAILPATTERN.toRegex()) &&
            !parent.gmail.matches(YAHOOMAILPATTERN.toRegex())) {
            Log.d("MyApp", "Invalid gmail")
            return false
        }

        if(!parent.contactNo.matches(CONTACTPATTERN.toRegex())){
            Log.d("MyApp", "Invalid contact")
            return false
        }

        return true

    }


    fun addChild(child: Child): Boolean{

        if(isEmptyString(
            child.childLastName,
            child.childFirstName,
            child.childMiddleName
        )) {
            Log.d("MyApp", "empty string")
            return false
        }

        if(!isValidName(
            child.childFirstName,
            child.childMiddleName,
            child.childLastName
        )){
            Log.d("MyApp", "invalid name ${child.childFirstName} ")
            Log.d("MyApp", "invalid name ${child.childMiddleName} ")
            Log.d("MyApp", "invalid name ${child.childLastName} ")
            return false
        }

        if(isZeroDouble(
            child.weight,
            child.height
        )){
            Log.d("MyApp", "zero double")
            return false
        }

        if(!isValidDaysDiff(child.birthDate)){
            Log.d("MyApp", "invalid days diff")
            return false
        }

        return true
    }

    private fun isValidName(vararg names:String): Boolean {
        return names.all { it.matches(NAMEPATTERN.toRegex()) }
    }

    private fun isEmptyString(vararg str:String):Boolean{
        return str.all {it == ""}
    }

    private fun isZeroDouble(vararg double: Double):Boolean{
        return double.all { it == 0.0 }
    }

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