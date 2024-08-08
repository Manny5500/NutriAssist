package com.example.projectcontact.util

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {

    //convert 20240422 --> April 04, 2024
    fun toLongDayFormat(date: String): String {
        val month = date.substring(4, 6).toInt()
        val monthName = DateFormatSymbols().months[month - 1]
        return monthName + " " + date.substring(6, 8) + ", " + date.substring(0, 4)
    }

    //convert 07/17/2020 --> 20240717
    fun convertDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }

    //return currentDate ---> 20240717
    fun currentDate():String{
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return currentDate.format(formatter)
    }

    //Jul 2024
    fun currentMonthYear() : String{
        val currentDate = LocalDate.now()
        val formatter =
            DateTimeFormatter.ofPattern("MMM yyyy")
        return currentDate.format(formatter)
    }

    //convert 07/17/2020 --> real Date
    @SuppressLint("SimpleDateFormat")
    fun toDateFormate(dateString : String ): Date {
        val dateFormat: DateFormat = SimpleDateFormat("d/M/yyyy")
        return dateFormat.parse(dateString)
    }

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("d/M/yyyy")
    val sdf1 = SimpleDateFormat("dd/MM/yyyy")
    val month = currentMonthYear()
    val dateNow: String = getStartDateLoading()
    val monthString = dateNow.substring(3, 5)
    val yearString = dateNow.substring(6, 10)
    val yearNow = yearString.toInt()
    val monthsWith31Days = setOf("01", "03", "05", "07", "08", "10", "12")
    val monthsWith30Days = setOf("04", "06", "09", "11")


    fun convertToTimeStamp(dateString: String, toOrfrom: String): Timestamp {
        var date = sdf.parse(dateString)
        if (toOrfrom == "to") {
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTime(date)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            date = calendar.getTime()
        }
        return Timestamp(date)
    }

    fun getStartDateLoading(): String {
        val date = Date()
        return sdf1.format(date)
    }

    fun getFirstoftheMonth() : String{
        return "01/$monthString/$yearString"
    }

    fun getLastoftheMonth() : String {
        return when (monthString) {
            in monthsWith31Days -> "31/$monthString/$yearString"
            in monthsWith30Days -> "30/$monthString/$yearString"
            else -> {
                if(yearNow % 4 == 0) "29/$monthString/$yearString"
                else "28/$monthString/$yearString"
            }
        }
    }

}