package com.example.projectcontact.ui.add_parent

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Parent
import com.example.projectcontact.repository.parent.ParentRepo
import com.example.projectcontact.util.ValidationUtil
import com.example.projectcontact.util.ValidationUtil.isContactNoValid
import com.example.projectcontact.util.ValidationUtil.isGmailValid
import com.example.projectcontact.util.ValidationUtil.isNameValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddParentViewModel @Inject constructor(
    private val parentRepo: ParentRepo
) : ViewModel(), Observable{

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    private val _parent = MutableLiveData<Parent>()
    val parent: LiveData<Parent> = _parent

    private val _parentList = MutableLiveData<List<Parent>>()
    val parentList: LiveData<List<Parent>> = _parentList

    private val _proceed = MutableLiveData<Boolean>()
    val proceed: LiveData<Boolean> = _proceed


    private val _errorFirstName = MutableLiveData<String>()
    val errorFirstName: LiveData<String> get() = _errorFirstName

    private val _errorMiddleName = MutableLiveData<String>()
    val errorMiddleName: LiveData<String> get() = _errorMiddleName

    private val _errorLastName = MutableLiveData<String>()
    val errorLastName : LiveData<String> get() = _errorLastName

    private val _errorGmail = MutableLiveData<String>()
    val errorGmail: LiveData<String> get() = _errorGmail

    private val _errorHouseNumber = MutableLiveData<String>()
    val errorHouseNumber: LiveData<String> get() = _errorHouseNumber

    private val _errorSitio = MutableLiveData<String>()
    val errorSitio: LiveData<String> get() = _errorSitio

    private val _errorMonthlyIncome = MutableLiveData<String>()
    val errorMonthlyIncome : LiveData<String> get() = _errorMonthlyIncome

    private val _errorContactNo = MutableLiveData<String>()
    val errorContactNo: LiveData<String> get() = _errorContactNo

    private fun validate() : Boolean {
        val firstName = parent.value?.parentFirstName.orEmpty()
        _errorFirstName.value = if (firstName.isBlank()) "First Name cannot be empty" else null
        if (firstName.isNotBlank()) _errorFirstName.value = if (!firstName.isNameValid()) "Invalid Name" else null

        val middleName = parent.value?.parentMiddleName.orEmpty()
        _errorMiddleName.value = if (middleName.isBlank()) "Middle Name cannot be empty" else null
        if (middleName.isNotBlank()) _errorMiddleName.value = if (!middleName.isNameValid()) "Invalid Name" else null

        val lastName = parent.value?.parentLastName.orEmpty()
        _errorLastName.value = if (lastName.isBlank()) "Last Name cannot be empty " else null
        if (lastName.isNotBlank()) _errorLastName.value = if (!lastName.isNameValid()) "Invalid Name" else null

        val gmail = parent.value?.gmail.orEmpty()
        _errorGmail.value = if (gmail.isBlank()) "Gmail cannot be empty" else null
        if (gmail.isNotBlank()) _errorGmail.value = if (!gmail.isGmailValid()) "Invalid Gmail" else null

        val houseNumber = parent.value?.houseNumber.orEmpty()
        _errorHouseNumber.value = if (houseNumber.isBlank()) "House Number cannot be empty" else null

        val sitio = parent.value?.sitio.orEmpty()
        _errorSitio.value = if (sitio.isBlank()) "Sitio cannot be empty" else null

        val monthlyIncome = parent.value?.monthlyIncome.orEmpty()
        _errorMonthlyIncome.value = if (monthlyIncome.isBlank()) "Monthly Income cannot be empty" else null

        val contactNo = parent.value?.contactNo.orEmpty()
        _errorContactNo.value = if (contactNo.isBlank()) "Contact Number cannot be empty" else null
        if(contactNo.isNotBlank()) _errorContactNo.value = if (!contactNo.isContactNoValid()) "Invalid Contact Number" else null

        return ValidationUtil.isErrorEmpty(
            _errorFirstName.value, _errorMiddleName.value, _errorLastName.value,
            _errorGmail.value, _errorHouseNumber.value, _errorSitio.value, _errorMonthlyIncome.value,
            _errorContactNo.value
        )
    }

    init{
        _parent.value = Parent()
        _parentList.value = emptyList()
    }

    fun saveParent(){
        if(!validate()) {
            _proceed.value = false
            return
        }
        _proceed.value = true
        viewModelScope.launch {
            try {
                parent.value?.let {
                        parentRepo.saveParent(it)
                }
                _parent.value = Parent()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to save temp Email $e")
            }
        }
    }

    fun getParents(){
        viewModelScope.launch {
            try{
                _parentList.value = parentRepo.getParents()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to get parents $e")
                _parentList.value = emptyList()
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

}