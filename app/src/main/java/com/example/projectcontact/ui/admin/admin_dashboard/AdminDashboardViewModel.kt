package com.example.projectcontact.ui.admin.admin_dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.filter.StatusPercentage.getMalnourishedPercentage
import com.example.projectcontact.util.filter.StatusPercentage.getNormalPercentage
import com.example.projectcontact.util.filter.StatusPercentage.getSevereCasesPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val childRepo: ChildRepo
) : ViewModel() {

    private val _childList = MutableLiveData<List<Child>>()
    val childList : LiveData<List<Child>> = _childList

    private val _normalPercentage = MutableLiveData<Float>()
    val normalPercentage : LiveData<Float> = _normalPercentage

    private val _malnourishedChildren = MutableLiveData<Float>()
    val malnourishedChildren : LiveData<Float> = _malnourishedChildren

    private val _severeCasesChildren = MutableLiveData<Float>()
    val severeCasesChildren : LiveData<Float> = _severeCasesChildren

    fun getChildren(){
        viewModelScope.launch{
            try {
                _childList.value = childRepo.getChildren()
                _normalPercentage.value = getNormalPercentage(_childList.value!!)
                _malnourishedChildren.value = getMalnourishedPercentage(_childList.value!!)
                _severeCasesChildren.value = getSevereCasesPercentage(_childList.value!!)
            }catch (e:Exception){
                Log.e("MyApp", "Failed to get children $e")
                _childList.value = emptyList()
            }
        }
    }



}