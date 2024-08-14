package com.example.projectcontact.ui.admin.admin_analytics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.AgeUtil.monthsBetweenToday
import com.example.projectcontact.util.BarangayUtil
import com.example.projectcontact.util.DateUtil
import com.example.projectcontact.util.DateUtil.getDateRange
import com.example.projectcontact.util.DateUtil.getStartDateRange
import com.example.projectcontact.util.DateUtil.toDateFormate
import com.example.projectcontact.util.DateUtil.toMidnight
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AdminAnalyticsViewModel @Inject constructor(
    private val childRepo: ChildRepo
) : ViewModel() {

    private var childList: List<Child> = emptyList()
    private val _lineChartData = MutableLiveData<Pair<List<Float>, List<String>>>()
    val lineChartData : LiveData<Pair<List<Float>, List<String>>> = _lineChartData

    private val _pieChartData = MutableLiveData<Pair<Float, Float>>()
    val pieChartData : LiveData<Pair<Float, Float>> = _pieChartData

    private val _barChart1Data = MutableLiveData<Pair<List<Float>, List<String>>>()
    val barChart1Data : LiveData<Pair<List<Float>, List<String>>> = _barChart1Data

    private val _barChart2Data = MutableLiveData<Pair<List<Float>, List<String>>>()
    val barChart2Data : LiveData<Pair<List<Float>, List<String>>> = _barChart2Data

    private val _tableData = MutableLiveData<Pair<List<Int>, List<String>>>()
    val tableData : LiveData<Pair<List<Int>, List<String>>> = _tableData

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
        getChildren()
    }

    fun setDataByPeriodDate(daysToAdd: Long){
        startDate = getStartDateRange(LocalDate.now(), daysToAdd)
        endDate = Date()
        dateList = getDateRange(startDate, endDate)
        getChildren()
    }

    private fun getChildren(){
        viewModelScope.launch{
            try {
                childList = childRepo.getChildren(Timestamp(startDate), Timestamp(endDate))
                _lineChartData.value = getLineChartData()
                _pieChartData.value = getPieChartData()
                _barChart1Data.value = getBarChart1Data()
                _barChart2Data.value = getBarChart2Data()
                _tableData.value = getTableData()
            }catch(e: Exception){
                Log.e("MyApp", "Failed to get children $e")
            }
        }
    }
    private fun getLineChartData() : Pair<List<Float>, List<String>> {
        val hello = childList.filter { it.dateAdded in startDate..endDate }
        val dataList = mutableListOf<Float>()
        val labelList = mutableListOf<String>()

        dateList.forEach { date ->
            val midnightDate = toMidnight(date.time)
            val count = hello.count { child -> toMidnight(child.dateAdded.time) == midnightDate }
            dataList.add(count.toFloat())
            labelList.add(DateUtil.sdf2.format(midnightDate))
        }
        return Pair(dataList, labelList)
    }

    private fun getPieChartData() : Pair<Float, Float>{
        val maleCount = childList.filter {it.sex == "Male"}.size.toFloat()
        val femaleCount = childList.filter {it.sex == "Female"}.size.toFloat()
        return Pair(maleCount, femaleCount)
    }

    private fun getBarChart1Data() :  Pair<List<Float>, List<String>> {
        val list = mutableListOf<Float>()
        val status = listOf("Underweight", "Severe Underweight", "Overweight",
            "Stunted", "Severe Stunted", "Wasted", "Severe Wasted")
        for( s in status){
            list.add(childList.filter{it.statusdb.contains(s)}.size.toFloat())
        }

        return Pair(list, status)
    }

    private fun getBarChart2Data() : Pair<List<Float>, List<String>>{
        val list = mutableListOf<Float>()
        val ageGroup = listOf("0-5", "6-11", "12-23", "24-35", "36-47", "48-59")
        val ageThreshold = listOf(-1, 6, 5, 12, 11, 24, 23, 36, 35, 48, 47, 60)
        for(i in ageThreshold.indices step 2){
            list.add(childList.filter{
                val age = monthsBetweenToday(toDateFormate(it.birthDate))
                age > ageThreshold[i] && age < ageThreshold[i+1]}
                .size
                .toFloat())
        }
        return Pair(list, ageGroup)
    }


    private fun getTableData() : Pair<List<Int>, List<String>>{
        val list = mutableListOf<Pair<Int, String>>()
        val barangayList = BarangayUtil.barangay
        barangayList.forEach {barangay->
            val count = childList.filter{it.barangay==barangay}.size
            list.add(Pair(count,barangay))
        }
        list.sortByDescending { it.first}
        val top6 = list.take(6)
        return Pair(top6.map { it.first }, top6.map{it.second})
    }

}
