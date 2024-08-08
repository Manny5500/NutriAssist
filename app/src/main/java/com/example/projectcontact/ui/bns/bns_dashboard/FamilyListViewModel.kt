package com.example.projectcontact.ui.bns.bns_dashboard

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcontact.model.Parent
import com.example.projectcontact.util.filter.Vulnerable.haveMalnourished
import com.example.projectcontact.util.filter.Vulnerable.lowIncome
import com.example.projectcontact.util.filter.Vulnerable.moreThanOneChild

class FamilyListViewModel:ViewModel(

) {
    private val _parentList = MutableLiveData<List<Parent>>().apply{
        value = emptyList()
    }

    val parentList: LiveData<List<Parent>> = _parentList

    private val _originalList = MutableLiveData<List<Parent>>()
    val originalList : LiveData<List<Parent>> = _originalList

    private val _filteredList = MutableLiveData<List<Parent>>()
    val filteredList : LiveData<List<Parent>> = _filteredList


    val categoryType = ObservableField<String>().apply {
        set("All")
    }

    val searchQuery = ObservableField<String>().apply{
        set("")
    }

    private val _isEmptyList = MutableLiveData<Boolean>().apply{
        value = false
    }

    val isEmptyList : LiveData<Boolean> = _isEmptyList


    fun setParentList(list:List<Parent>){
        _parentList.value = list
        _originalList.value = list
    }

    fun setSearchQuery(query: String){
        searchQuery.set(query)
        filter()
    }

    fun filter(){
        _filteredList.value = if(searchQuery.get()?.isEmpty()!!){
            _originalList.value
        }else{
            _originalList.value?.filter{
                it.parentFirstName.contains(searchQuery.get()!!,  true)
            }
        }
        _filteredList.value = when(categoryType.get()){
            "More than 1 child" -> moreThanOneChild(_filteredList.value!!)
            "Low Income" -> lowIncome(_filteredList.value!!)
            "Have a Malnourished Child" -> haveMalnourished(_filteredList.value!!)
            else -> _filteredList.value!!
        }


        if(_filteredList.value!!.isEmpty()){
            _isEmptyList.value = true
        }else{
            _isEmptyList.value = false
        }

    }

}