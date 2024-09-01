package com.example.projectcontact.ui.admin.summary_report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.DateUtil.monthNow
import com.example.projectcontact.util.data_analysis.summary_report.ConsolidatedData
import com.example.projectcontact.util.data_analysis.summary_report.ListData
import com.example.projectcontact.util.data_analysis.summary_report.SummaryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SummaryReportViewModel @Inject constructor(
    private val childRepo : ChildRepo
) : ViewModel() {

    private val _listData = MutableLiveData<List<Array<Array<String>>>>()
    val listData : LiveData<List<Array<Array<String>>>> = _listData

    private val _consoData = MutableLiveData<List<Array<Array<String>>>>()
    val consoData : LiveData<List<Array<Array<String>>>> = _consoData

    private val _summaryData = MutableLiveData<List<Array<Array<String>>>>()
    val summaryData : LiveData<List<Array<Array<String>>>> = _summaryData

    private val _childrenList = MutableLiveData<List<Child>>()
    val childrenList : LiveData<List<Child>> = _childrenList


    private var monthAdded = monthNow()
    private var barangay = "Alipit"

    init{
        getData()
    }
    fun setMonthAdded(monthAdded : String){
        this.monthAdded = monthAdded
        getData()
    }
    fun setBarangay(barangay: String){
        this.barangay = barangay
        getData()
    }

    fun setMonthAddedandBarangay(monthAdded: String, barangay: String){
        this.monthAdded = monthAdded
        this.barangay = barangay
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            try {
                val childList = childRepo.getChildren(monthAdded, barangay)
                _childrenList.value = childList
                _listData.value = with(ListData(childList)){
                    generateTableData(countNow())
                }
                _consoData.value = with(ConsolidatedData(childList)){
                    val pair = countNow()
                    generateTableData(pair.first, pair.second)
                }
                _summaryData.value = with(SummaryData(childList)){
                    getTableData()
                }
            }catch (e: Exception){
                Log.e("MyApp", "Failed to process child data $e")
            }
        }
    }
}