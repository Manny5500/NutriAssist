package com.example.projectcontact.ui.admin.master_list

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.DateUtil.monthNow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterListViewModel  @Inject constructor(
    private val childRepo: ChildRepo
): ViewModel(){
    private val _childrenList = MutableLiveData<List<Child>>()
    val childrenList : LiveData<List<Child>> = _childrenList

    private val searchQuery = ObservableField<String>().apply{
        set("")
    }

    private val _filteredList = MutableLiveData<List<Child>>()
    val filteredList : LiveData<List<Child>> = _filteredList


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


    fun setSearchQuery(s: String){
        searchQuery.set(s)
        filter()
    }

    private fun getData(){
        viewModelScope.launch {
            try {
                _childrenList.value = childRepo.getChildren(monthAdded, barangay)
                filter()
            }catch (e: Exception){
                Log.e("MyApp", "Failed to process child data $e")
            }
        }
    }

    fun filter(){
        _filteredList.value = if(searchQuery.get()?.isEmpty()!!){
            _childrenList.value
        }else{
            _childrenList.value?.filter{
                it.childFirstName.contains(searchQuery.get()!!, true)
            }
        }
    }
}