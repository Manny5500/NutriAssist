package com.example.projectcontact.ui.admin.admin_analytics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.demographics.AgeDemographics
import com.example.projectcontact.model.demographics.BarangayDemographics
import com.example.projectcontact.model.demographics.GenderDemographics
import com.example.projectcontact.model.demographics.HistoricalDataDemographics
import com.example.projectcontact.model.demographics.StatusDemographics
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.AgeUtil.monthsBetweenToday
import com.example.projectcontact.util.DataValues
import com.example.projectcontact.util.DataValues.statusList
import com.example.projectcontact.util.DateUtil
import com.example.projectcontact.util.DateUtil.dateToLocalDate
import com.example.projectcontact.util.DateUtil.getDateRange
import com.example.projectcontact.util.DateUtil.getPreviousEndDateRange
import com.example.projectcontact.util.DateUtil.getPreviousStartDateRange
import com.example.projectcontact.util.DateUtil.getStartDateRange
import com.example.projectcontact.util.DateUtil.subtractTwoDates
import com.example.projectcontact.util.DateUtil.toDateFormate
import com.example.projectcontact.util.DateUtil.toMidnight
import com.example.projectcontact.util.NumUtil.percentage
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject
import kotlin.math.abs


@HiltViewModel
class AdminAnalyticsViewModel @Inject constructor(
    private val childRepo: ChildRepo
) : ViewModel() {

    private var childList: List<Child> = emptyList()
    private var previousChildList : List<Child> = emptyList()

    private val _historicalDemographics = MutableLiveData<HistoricalDataDemographics>()
    val historicalDataDemographics : LiveData<HistoricalDataDemographics> = _historicalDemographics

    private val _genderDemographics = MutableLiveData<GenderDemographics>()
    val genderDemographics : LiveData<GenderDemographics> = _genderDemographics

    private val _statusDemographics = MutableLiveData<StatusDemographics>()
    val statusDemographics : LiveData<StatusDemographics> = _statusDemographics

    private val _ageDemographics = MutableLiveData<AgeDemographics>()
    val ageDemographics : LiveData<AgeDemographics> = _ageDemographics

    private val _barangayDemographics = MutableLiveData<BarangayDemographics>()
    val barangayDemographics : LiveData<BarangayDemographics> = _barangayDemographics

    private val _periodType = MutableLiveData<String>().apply {
        value = "week"
    }

    val periodType : LiveData<String> = _periodType

    private val _dateRangeVal = MutableLiveData<String>().apply{
        value = ""
    }
    val dateRangeVal : LiveData<String> = _dateRangeVal

    private val _hideDateRangeTab = MutableLiveData<Boolean>().apply{
        value = true
    }
    val hideDateRangeTab : LiveData<Boolean> = _hideDateRangeTab

    private var daysToSubtract: Long = 7
    private lateinit var startDate: Date
    private lateinit var endDate: Date
    private lateinit var dateList: List<Date>


    fun setDateRangeVal(string: String){
        _dateRangeVal.value = string
    }

    fun setPeriodType(string: String, boolean: Boolean = false){
        _periodType.value = string
        _hideDateRangeTab.value = boolean
    }
    fun setDataByCostumedDate(dateFrom: String, dateTo: String){
        startDate = toDateFormate(dateFrom)
        endDate = toDateFormate(dateTo)
        dateList = getDateRange(startDate, endDate)
        daysToSubtract = subtractTwoDates(endDate, startDate)
        getChildren()
    }

    fun setDataByPeriodDate(daysToSubtract: Long){
        this.daysToSubtract = daysToSubtract
        startDate = getStartDateRange(LocalDate.now(), this.daysToSubtract)
        endDate = Date()
        dateList = getDateRange(startDate, endDate)
        getChildren()
    }

    private fun getChildren(){
        viewModelScope.launch{
            try {
                childList = childRepo.getChildren(Timestamp(startDate), Timestamp(endDate))
                val previousStartDate = getPreviousStartDateRange(dateToLocalDate(endDate), daysToSubtract)
                val previousEndDate = getPreviousEndDateRange(dateToLocalDate(endDate), daysToSubtract)
                previousChildList = childRepo.getChildren(Timestamp(previousStartDate), Timestamp(previousEndDate))
                setHistoricalDataDemographics()
                setGenderDemographics()
                setStatusDemographics()
                setAgeDemographics()
                setBarangayDemographics()
            }catch(e: Exception){
                Log.e("MyApp", "Failed to get children $e")
            }
        }
    }

    private fun setHistoricalDataDemographics(){
        val filteredList = childList.filter { it.dateAdded in startDate..endDate }
        val dataList = mutableListOf<Float>()
        val labelList = mutableListOf<String>()


        dateList.forEach { date ->
            val midnightDate = toMidnight(date.time)
            val count = filteredList.count { child -> toMidnight(child.dateAdded.time) == midnightDate }
            dataList.add(count.toFloat())
            labelList.add(DateUtil.sdf2.format(midnightDate))
        }

        val a = filteredList.size > previousChildList.size
        val b = filteredList.size < previousChildList.size
        val c = filteredList.isEmpty()
        val d = previousChildList.isEmpty()
        val statusColor = when{
            a && d -> "#000000"
            b && c-> "#000000"
            a -> "#FF0000"
            b -> "#097969"
            else -> "#000000"
        }

        val size = if(previousChildList.isEmpty()) 1  else previousChildList.size
        val difference = abs(filteredList.size - previousChildList.size)
        val percentage = difference.toFloat()/size * 100

        val observation = when{
            a && d -> "-"
            b && c -> "-"
            a -> "$percentage % more than the previous $daysToSubtract days"
            b -> "$percentage % less than the previous $daysToSubtract days"
            else -> "Just as the same"
        }

        _historicalDemographics.value = HistoricalDataDemographics(
            Pair(dataList, labelList),
            filteredList.size,
            previousChildList.size,
            observation,
            statusColor
        )
    }

    private fun setGenderDemographics(){
        val size = childList.size.toFloat()
        val maleCount = childList.filter {it.sex == "Male"}.size.toFloat()
        val femaleCount = childList.filter {it.sex == "Female"}.size.toFloat()
        val array = Array(2) { arrayOf<String>()}
        array[0] = arrayOf("Male", maleCount.toInt().toString(),  percentage(maleCount, size))
        array[1] = arrayOf("Female",femaleCount.toInt().toString(),  percentage(femaleCount, size) )
        _genderDemographics.value = GenderDemographics(
            Pair(maleCount, femaleCount),
            array
        )
    }

    private fun setStatusDemographics(){
        val list = mutableListOf<Float>()
        statusList.forEach{ s->
            list.add(childList.filter{it.statusdb.contains(s)}.size.toFloat())
        }
        _statusDemographics.value = StatusDemographics(
            Pair(list, statusList),
            twoColumnsTableData(7, statusList, list)
        )
    }
    private fun setAgeDemographics(){
        val list = mutableListOf<Float>()
        val ageGroup = listOf("0-5", "6-11", "12-23", "24-35", "36-47", "48-59")
        val ageThreshold = listOf(0, 5, 6, 11, 12, 23, 24, 35, 36, 47, 48, 59)
        for(i in ageThreshold.indices step 2){
            list.add(childList.filter{
                val age = monthsBetweenToday(toDateFormate(it.birthDate))
                age in ageThreshold[i] .. ageThreshold[i+1]}
                .size
                .toFloat())
        }
        _ageDemographics.value = AgeDemographics(
            Pair(list, ageGroup),
            twoColumnsTableData(6, ageGroup, list)
        )

    }

    private fun setBarangayDemographics(){
        val list = mutableListOf<Pair<Int, String>>()
        DataValues.barangay.forEach {barangay->
            val count = childList.filter{it.barangay==barangay}.size
            list.add(Pair(count,barangay))
        }
        list.sortByDescending { it.first}
        //not use the generic twoColumnsTableData because different dataStructure
        val array = Array(6) { arrayOf<String>() }
        for(i in array.indices){
            array[i] = arrayOf(list[i].second, list[i].first.toString())
        }
        _barangayDemographics.value = BarangayDemographics(array)
    }

    private fun twoColumnsTableData(
        size: Int, labelList: List<String>, dataList: List<Float>
    ) : Array<Array<String>>
    {
        val array = Array(size) { arrayOf<String>()}
        for(i in array.indices){
            array[i] = arrayOf(labelList[i], dataList[i].toInt().toString())
        }
        return array
    }
}
