package com.example.projectcontact.ui.admin.prevalance_report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Barangay
import com.example.projectcontact.repository.barangay.BarangayRepo
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.DateUtil.monthNow
import com.example.projectcontact.util.Notation.statsNotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrevalanceReportViewModel @Inject constructor(
    private val childRepo: ChildRepo,
    private val barangayRepo: BarangayRepo
): ViewModel(){
    private val _barangayList = MutableLiveData<List<Barangay>>()
    val barangayList : LiveData<List<Barangay>> = _barangayList

    private val _townPrevalance = MutableLiveData<Float>()
    val townPrevalance : LiveData<Float> = _townPrevalance

    private val _townTotalCase = MutableLiveData<Int>()
    val townTotalCase : LiveData<Int> = _townTotalCase

    private var monthAdded = monthNow()
    private var status = listOf("Underweight", "Severe Underweight")

    fun setMonthAdded(monthAdded: String){
        this.monthAdded = monthAdded
        getData()
    }

    fun setStatus(status: List<String>){
        this.status = status
        getData()
    }

    init{
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            try {
                val childList = childRepo.getChildren(monthAdded)
                val barangayList = barangayRepo.getBarangays()
                barangayList.forEach {
                    val filteredChildList = childList.filter{ child->
                        child.barangay == it.barangay
                    }
                    val childrenSize = filteredChildList.size
                    val indexToCompare = when(statsNotation(status[0])){
                        "ST" -> 1
                        "OW" -> 2
                        "MW" -> 2
                        else -> 0
                    }
                    it.totalCase = filteredChildList.count{child->
                        child.status[indexToCompare] in status
                    }
                    it.normalCase = filteredChildList.count{child->
                        child.status[indexToCompare] == "Normal" || child.status[indexToCompare] == ""
                    }
                    it.optCoverage = (childrenSize.toFloat()/it.estimatedChildren.toFloat())*100.0f
                    if(childrenSize>0){
                        it.normalRate = (it.normalCase.toFloat()/childrenSize.toFloat())*100.0f
                        it.totalRate = (it.totalCase.toFloat()/childrenSize.toFloat())*100.0f
                    }
                    it.caseLabel= status.joinToString(" & ") { s -> statsNotation(s) }
                }

                val townEstimatedChildren = barangayList.sumOf{it.estimatedChildren}
                val townTotalCase  = barangayList.sumOf{it.totalCase}
                _townTotalCase.value = townTotalCase
                _townPrevalance.value = (townTotalCase.toFloat()/townEstimatedChildren.toFloat())*100.0f
                var i = 1
                _barangayList.value = barangayList.sortedByDescending { it.totalCase }.map{
                    it.copy(position = i++)
                }
            }catch (e: Exception){
                Log.e("MyApp", "Failed to process data $e")
            }
        }
    }
}