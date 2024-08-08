package com.example.projectcontact.ui.bns.bns_dashboard

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Barangay
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.Parent
import com.example.projectcontact.repository.barangay.BarangayRepo
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.repository.parent.ParentRepo
import com.example.projectcontact.util.DateUtil.convertToTimeStamp
import com.example.projectcontact.util.DateUtil.getFirstoftheMonth
import com.example.projectcontact.util.DateUtil.getLastoftheMonth
import com.example.projectcontact.util.filter.FeedingProgram.fpList
import com.example.projectcontact.util.filter.GulayansaBakuran.gsbList
import com.example.projectcontact.util.filter.Vulnerable.vList
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BNSDashboardViewModel @Inject constructor(
    private val childRepo: ChildRepo,
    private val parentRepo: ParentRepo,
    private val barangayRepo: BarangayRepo
): ViewModel() {

    private val _parentList  = MutableLiveData<List<Parent>>().apply {
        value = emptyList()
    }
    val parentList:LiveData<List<Parent>> = _parentList

    private val _childList = MutableLiveData<List<Child>>()
    val childList : LiveData<List<Child>> = _childList

    private val _barangay =  MutableLiveData<Barangay>()
    val barangay : LiveData<Barangay> = _barangay

    private val _vulnerableList = MutableLiveData<List<Parent>>().apply {
        value = emptyList()
    }
    val vulnerableList : LiveData<List<Parent>> = _vulnerableList

    private val _gulayanList = MutableLiveData<List<Parent>>().apply{
        value = emptyList()
    }
    val gulayanList : LiveData<List<Parent>> = _gulayanList

    private val _feedingList = MutableLiveData<List<Child>>().apply{
        value = emptyList()
    }
    val feedingList : LiveData<List<Child>> = _feedingList

    val fromDate = ObservableField<String>().apply{
        set(getFirstoftheMonth())
    }

    val toDate = ObservableField<String>().apply{
        set(getLastoftheMonth())
    }

    private val _dateChange = MutableLiveData<Unit>()
    val dateChange : LiveData<Unit> = _dateChange

    val from  = ObservableField<Timestamp>()
    val to = ObservableField<Timestamp>()

    val familyCount = ObservableField<Int>()
    val vulnerableCount = ObservableField<Int>()
    val gulayanCount = ObservableField<Int>()
    val feedingCount = ObservableField<Int>()
    val populationCount = ObservableField<Int>()
    val childCount = ObservableField<Int>()

    private var previousFromDate = ""
    private var previousToDate = ""

    fun cancel(){
        fromDate.set(previousFromDate)
        toDate.set(previousToDate)
    }

    fun init(){
        previousFromDate = fromDate.get().toString()
        previousToDate = toDate.get().toString()
        from.set(convertToTimeStamp(fromDate.get().toString(), "from"))
        to.set(convertToTimeStamp(toDate.get().toString(), "to"))
        _dateChange.value = Unit
        getParentsandChildren()
        getBarangay()
    }

    private fun getParentsandChildren(){
        viewModelScope.launch{
            try {
                _childList.value = childRepo.getChildren(from.get()!!, to.get()!!)
                _parentList.value = parentRepo.getParents()

                for(parent in _parentList.value!!) {
                    parent.children = _childList.value!!.filter { child->
                        child.gmail == parent.gmail
                    }
                }


                //to filter the parent that has examined children on the specified date range
                //parent with no examined children are not included
                _parentList.value = _parentList.value!!.filter {parent->
                    parent.children.isNotEmpty()
                }

                setFamily()
                setVulnerable()
                setGulayan()
                setFeeding()
            }catch (e:Exception){
                _parentList.value = emptyList()
                Log.e("MyApp", "Failed to get data $e")
            }
        }
    }

    private fun getBarangay(){
        viewModelScope.launch {
            try {
                _barangay.value = barangayRepo.getBarangay("Bucal")
                populationCount.set(_barangay.value?.population)
                childCount.set(_barangay.value?.estimatedChildren)
            }catch (e:Exception){
                Log.e("MyApp", "Failed to get barangay $e")
            }
        }
    }

    private fun setFamily(){
        familyCount.set(_parentList.value?.size)
    }

    private fun setVulnerable(){
        _vulnerableList.value = vList(_parentList.value!!)
        vulnerableCount.set(_vulnerableList.value!!.size)
    }

    private fun setGulayan(){
        _gulayanList.value = gsbList(_parentList.value!!)
        gulayanCount.set(_gulayanList.value!!.size)
    }

    private fun setFeeding(){
        _feedingList.value = fpList(_childList.value!!)
        feedingCount.set(_feedingList.value!!.size)
    }

}