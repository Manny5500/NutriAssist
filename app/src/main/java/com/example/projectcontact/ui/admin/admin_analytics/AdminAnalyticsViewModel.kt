package com.example.projectcontact.ui.admin.admin_analytics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
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


}
