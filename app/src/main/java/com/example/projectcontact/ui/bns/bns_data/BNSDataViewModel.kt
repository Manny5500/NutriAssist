package com.example.projectcontact.ui.bns.bns_data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.Child
import com.example.projectcontact.repository.child.ChildRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BNSDataViewModel @Inject constructor(
    private val repo: ChildRepo
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _children = MutableLiveData<List<Child>>()
    val children:LiveData<List<Child>> = _children

    private val _child = MutableLiveData<Child>()
    var child:LiveData<Child> = _child

    fun fetchData(){
        viewModelScope.launch {
            try{
                _children.value = repo.getChildren()
            }catch (e:Exception){
                Log.e("tagger", "" + e)
            }
        }
    }

    fun deleteChild(childId: String){
        viewModelScope.launch{
            try{
                repo.deleteChild(childId)
                delay(2000)
                fetchData()
            }catch (e:Exception){
                Log.e("MyApp", "Failed delete to child $e")
            }
        }
    }




}