package com.example.projectcontact.ui.history

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.History
import com.example.projectcontact.repository.history.HistoryRepo
import com.example.projectcontact.util.AgeUtil
import com.example.projectcontact.util.DateUtil
import com.example.projectcontact.util.DateUtil.currentDate
import com.example.projectcontact.util.status.StatusFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: HistoryRepo
):ViewModel() {

    private val _historyList =  MutableLiveData<List<History>>()
    val historyList : LiveData<List<History>> = _historyList

    private val _history = MutableLiveData<History>()
    val history : LiveData<History> = _history

    val status = ObservableField<String>()

    var fullName = ""
    var ageDisplay = ""
    var ageInMonth = 0


    init{
        _history.value = History()
        status.set("")
    }


    fun fetchData(fullName: String){
        viewModelScope.launch {
            try {
                _historyList.value = repo.getHistory(fullName)
            }catch (e:Exception){
                Log.e("MyApp", "Failed to get history $e")
            }
        }
    }

    fun updateHistory(){
        viewModelScope.launch{
            try{
                _history.value?.status = StatusFetcher().individualTest(
                    ageInMonth, _history.value!!.weight,
                    _history.value!!.height, _history.value!!.sex
                ).toList()

                _history.value?.statusdb = StatusFetcher().toSimpleList(
                    _history.value?.status!!
                )
                 status.set( _history.value?.toFormattedStatus())

                _history.value?.let { repo.updateHistory(it, fullName) }
            }catch (e: Exception){
                Log.e("MyApp", "Failed to update history $e")
            }
        }
    }

    fun setHistory(history: History){
        _history.value = history
        status.set(history.toFormattedStatus())
    }

    fun setAge(startDate:String, endDate:String){
        ageInMonth = AgeUtil.monthsBetween(
            DateUtil.convertDate(startDate),
            endDate)

        ageDisplay = "Age : $ageInMonth month/s"
    }

    fun saveHistory(child:Child){
        viewModelScope.launch{
            try {
                val fullName = child.fullestName
                _history.value?.height  = child.height
                _history.value?.weight = child.weight
                _history.value?.id = currentDate()
                _history.value?.statusdb = child.statusdb
                _history.value?.status = child.status
                _history.value?.sex = child.sex

                history.value?.let { repo.saveHistory(it, fullName) }
            }catch (e:Exception){
                Log.e("MyApp", "Failed to save history $e")
            }
        }
    }


}