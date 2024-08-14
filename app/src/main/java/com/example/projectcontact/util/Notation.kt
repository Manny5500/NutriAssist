package com.example.projectcontact.util


object Notation {

    fun statsNotation(stats: String):String{
        return when(stats){
            "" -> "N"
            "Normal" -> "N"
            "Underweight" -> "UW"
            "Severe Underweight" -> "SUW"
            "Stunted" -> "ST"
            "Severe Stunted" -> "SST"
            "Tall" -> "T"
            "Wasted" -> "MW"
            "Severe Wasted" -> "SW"
            "Overweight" -> "OW"
            "Obese" -> "OB"
            else  -> ""
        }
    }
    fun genderNotation(gender: String): String {
        return if (gender == "Male") {
            "M"
        } else {
            "F"
        }
    }
}