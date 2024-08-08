package com.example.projectcontact.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object AgeUtil {

    //20240717 - 20200717 in months
    fun monthsBetween(startDateString: String, endDateString: String): Int {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val startDate = dateFormat.parse(startDateString)!!
        val endDate = dateFormat.parse(endDateString)!!

        val calendar = Calendar.getInstance()

        calendar.time = startDate
        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH)

        calendar.time = endDate
        val endYear = calendar.get(Calendar.YEAR)
        val endMonth = calendar.get(Calendar.MONTH)

        return abs((endYear - startYear) * 12 + (endMonth - startMonth))
    }

    //Today - Days in Month
    fun monthsBetweenToday(givenDate: Date?): Int {
        if (givenDate != null) {
            val currentCalendar: Calendar = Calendar.getInstance()
            val givenCalendar: Calendar = Calendar.getInstance()
            givenCalendar.setTime(givenDate)
            val years: Int = currentCalendar.get(Calendar.YEAR) - givenCalendar.get(Calendar.YEAR)
            val months: Int =
                currentCalendar.get(Calendar.MONTH) - givenCalendar.get(Calendar.MONTH)
            return years * 12 + months
        }
        return 0
    }

    //Today - Days in Year
    fun yearsBetweenToday(givenDate: Date?): Int{
        if(givenDate!=null){
            val currentCalendar: Calendar = Calendar.getInstance()
            val givenCalendar: Calendar = Calendar.getInstance()
            givenCalendar.setTime(givenDate)
            return  currentCalendar.get(Calendar.YEAR) - givenCalendar.get(Calendar.YEAR)
        }
        return 0
    }

    //Today - Days
    fun daysBetweenToday(givenDate: Date?): Int {
        if (givenDate != null) {
            val currentCalendar = Calendar.getInstance()
            val givenCalendar = Calendar.getInstance()
            givenCalendar.setTime(givenDate)
            val currentTimeInMillis = currentCalendar.getTimeInMillis()
            val givenTimeInMillis = givenCalendar.getTimeInMillis()
            val differenceInMillis = currentTimeInMillis - givenTimeInMillis
            return (differenceInMillis / (24 * 60 * 60 * 1000)).toInt()
        }
        return 0
    }

    //Today - Days in millis
    fun daysBetweenTodayMill(givenDate: Date?): Long {
        if (givenDate != null) {
            val currentCalendar = Calendar.getInstance()
            val givenCalendar = Calendar.getInstance()
            givenCalendar.setTime(givenDate)
            val currentTimeInMillis = currentCalendar.getTimeInMillis()
            val givenTimeInMillis = givenCalendar.getTimeInMillis()
            return currentTimeInMillis - givenTimeInMillis
        }
        return 0
    }

}