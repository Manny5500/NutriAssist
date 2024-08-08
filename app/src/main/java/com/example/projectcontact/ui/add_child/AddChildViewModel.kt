package com.example.projectcontact.ui.add_child

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.Parent
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.util.AgeUtil
import com.example.projectcontact.util.DateUtil
import com.example.projectcontact.util.ValidationUtil
import com.example.projectcontact.util.ValidationUtil.checkedDaysDiff
import com.example.projectcontact.util.ValidationUtil.isErrorEmpty
import com.example.projectcontact.util.ValidationUtil.isNameValid
import com.example.projectcontact.util.status.StatusFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChildViewModel @Inject constructor(
    private val repo:ChildRepo
):ViewModel(), Observable{

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    private val _child = MutableLiveData<Child> ()
    val child : LiveData<Child> = _child

    private val _age = MutableLiveData<Int>()
    var age:LiveData<Int> = _age

    private val _showDialog = MutableLiveData<Boolean>()
    var showDialog:LiveData<Boolean> = _showDialog

    private val _errorFirstName = MutableLiveData<String>()
    val errorFirstName: LiveData<String> get() = _errorFirstName

    private val _errorMiddleName = MutableLiveData<String>()
    val errorMiddleName: LiveData<String> get() = _errorMiddleName

    private val _errorLastName = MutableLiveData<String>()
    val errorLastName : LiveData<String> get() = _errorLastName

    private val _errorHeight = MutableLiveData<String>()
    val errorHeight: LiveData<String> get() = _errorHeight

    private val _errorWeight = MutableLiveData<String>()
    val errorWeight: LiveData<String> get() = _errorWeight

    private val _errorExpectedDate = MutableLiveData<String>()
    val errorExpectedDate: LiveData<String> get() = _errorExpectedDate

    private val _errorSex = MutableLiveData<String>()
    val errorSex : LiveData<String> get() = _errorSex

    private val _errorBirthDate = MutableLiveData<String>()
    val errorBirthDate: LiveData<String> get() = _errorBirthDate

    private lateinit var  parent: Parent

    private fun validate() : Boolean {
        val firstName = child.value?.childFirstName.orEmpty()
        _errorFirstName.value = if (firstName.isBlank()) "First Name cannot be empty" else null
        if (firstName.isNotBlank()) _errorFirstName.value = if (!firstName.isNameValid()) "Invalid Name" else null

        val middleName = child.value?.childMiddleName.orEmpty()
        _errorMiddleName.value = if (middleName.isBlank()) "Middle Name cannot be empty" else null
        if (middleName.isNotBlank()) _errorMiddleName.value = if (!middleName.isNameValid()) "Invalid Name" else null

        val lastName = child.value?.childLastName.orEmpty()
        _errorLastName.value = if (lastName.isBlank()) "Last Name cannot be empty " else null
        if (lastName.isNotBlank()) _errorLastName.value = if (!lastName.isNameValid()) "Invalid Name" else null

        val height = child.value?.height
        _errorHeight.value = if (height == 0.0) "Invalid Height" else null

        val weight = child.value?.weight
        _errorWeight.value = if (weight == 0.0) "Invalid Weight" else null

        val expectedDate = child.value?.expectedDate.orEmpty()
        _errorExpectedDate.value = if (expectedDate.isBlank()) "Expected Date cannot be empty" else null

        val sex = child.value?.sex.orEmpty()
        _errorSex.value = if (sex.isBlank()) "Sex Field cannot be empty" else null

        val birthDate = child.value?.birthDate.orEmpty()
        _errorBirthDate.value = if (birthDate.isBlank()) "Birthdate cannot be empty" else null
        if(birthDate.isNotBlank()) _errorBirthDate.value = if (!birthDate.checkedDaysDiff()) "Invalid Date" else null

        return isErrorEmpty(_errorFirstName.value, _errorMiddleName.value, _errorLastName.value,
            _errorHeight.value, _errorWeight.value, _errorExpectedDate.value,  _errorSex.value,
            _errorBirthDate.value)
    }

    init{
        _child.value = Child()
    }

    fun setParent(parent: Parent){
        this.parent = parent
    }

    fun addChild(){
        if(!validate()) return
        viewModelScope.launch{
            try{
                _age.value = AgeUtil.monthsBetweenToday(
                    child.value?.let { DateUtil.toDateFormate(it.birthDate) }
                )
                setStatus()

                _child.value?.parentFirstName = parent.parentFirstName
                _child.value?.parentMiddleName = parent.parentMiddleName
                _child.value?.parentLastName = parent.parentLastName
                _child.value?.gmail = parent.gmail
                _child.value?.houseNumber = parent.houseNumber
                _child.value?.sitio = parent.sitio
                _child.value?.phoneNumber = parent.contactNo


                child.value?.let {
                        repo.addChild(it)
                        _showDialog.value = true
                    _child.value = Child()
                }
            }catch (e:Exception){
                Log.e("MyApp", "Failed to add child $e")
            }
        }
    }

    fun setStatus(){
        _child.value?.status  = _child.value?.let {
            StatusFetcher().individualTest(
                _age.value!!,
                it.weight,
                it.height,
                it.sex)
                .toList()
        }!!

        _child.value?.statusdb = _child.value?.status?.let {
            StatusFetcher().toSimpleList(it) }!!
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }


}