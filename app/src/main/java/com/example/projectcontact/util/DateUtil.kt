package com.example.projectcontact.util

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
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
    private fun currentMonthYear() : String{
        val currentDate = LocalDate.now()
        val formatter =
            DateTimeFormatter.ofPattern("MMM yyyy")
        return currentDate.format(formatter)
    }

    //convert 07/17/2020 --> real Date
    @SuppressLint("SimpleDateFormat")
    fun toDateFormate(dateString : String ): Date {
        return sdf.parse(dateString)
    }

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("d/M/yyyy")
    @SuppressLint("SimpleDateFormat")
    val sdf1 = SimpleDateFormat("dd/MM/yyyy")
    val month = currentMonthYear()
    private val dateNow: String = getStartDateLoading()
    private val monthString = dateNow.substring(3, 5)
    private val yearString = dateNow.substring(6, 10)
    private val yearNow = yearString.toInt()
    private val monthsWith31Days = setOf("01", "03", "05", "07", "08", "10", "12")
    private val monthsWith30Days = setOf("04", "06", "09", "11")
    @SuppressLint("SimpleDateFormat")
    val sdf2 = SimpleDateFormat("MM-dd")
    @SuppressLint("SimpleDateFormat")
    val sdf3 = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
    //sdf3.timeZone = TimeZone.getTimeZone("GMT+08:00")
    private val zoneId = ZoneId.of("GMT+08:00")


    fun convertToTimeStamp(dateString: String, toOrfrom: String): Timestamp {
        var date = sdf.parse(dateString)
        if (toOrfrom == "to") {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            date = calendar.time
        }
        return Timestamp(date)
    }

    fun subtractTwoDates(date1: Date, date2: Date): Long {
        val diffInMillis = date1.time - date2.time
        return diffInMillis / (1000 * 60 * 60 * 24)
    }
    private fun getStartDateLoading(): String {
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

    fun getDateRange(startDate: Date, endDate: Date): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val dates = mutableListOf<Date>()
        while (calendar.time.before(endDate) || calendar.time.equals(endDate)) {
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dates
    }

    //--> any timestamps will be on midnight
    fun toMidnight(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    fun dateToLocalDate(date: Date): LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }


    //---start days becuase the end date is the current date
    fun getStartDateRange(date: LocalDate, daysToSubtract: Long) : Date{
        val endDate = date.minusDays(daysToSubtract)
        return Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun getPreviousEndDateRange(date: LocalDate, daysToSubtract: Long) : Date{
        val startDate = date.minusDays(daysToSubtract+1)
        return Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun getPreviousStartDateRange(date: LocalDate, daysToSubtract: Long) : Date{
        val endDate = date.minusDays(daysToSubtract * 2)
        return Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun getFirstDayoftheMonth(date: LocalDate): Date {
        val firstDay = date.with(TemporalAdjusters.firstDayOfMonth())
        val firstDayZoned = ZonedDateTime.of(firstDay.atStartOfDay(), zoneId)
        return Date.from(firstDayZoned.toInstant())
    }

    fun getLastDayoftheMonth(date: LocalDate): Date{
        val lastDay = date.with(TemporalAdjusters.lastDayOfMonth())
        val lastDayZoned = ZonedDateTime.of(lastDay.atStartOfDay(), zoneId)
        return Date.from(lastDayZoned.toInstant())
    }


    fun getFirstDayOfYear(date: LocalDate): Date {
        val firstDay = date.with(TemporalAdjusters.firstDayOfYear())
        val firstDayZoned = ZonedDateTime.of(firstDay.atStartOfDay(), zoneId)
        return Date.from(firstDayZoned.toInstant())
    }

    fun getLastDayOfYear(date: LocalDate): Date {
        val lastDay = date.with(TemporalAdjusters.lastDayOfYear())
        val lastDayZoned = ZonedDateTime.of(lastDay.atStartOfDay(), zoneId)
        return Date.from(lastDayZoned.toInstant())
    }

}