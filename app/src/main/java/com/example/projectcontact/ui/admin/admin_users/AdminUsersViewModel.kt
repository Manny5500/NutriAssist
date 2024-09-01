package com.example.projectcontact.ui.admin.admin_users

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.repository.user.UserRepo
import com.example.projectcontact.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminUsersViewModel @Inject constructor(
    private val userRepo:UserRepo
): ViewModel() {

    private var userList: List<User> = emptyList()

    private val _filteredList = MutableLiveData<List<User>>()
    val filteredList : LiveData<List<User>> = _filteredList

    private val searchQuery = ObservableField<String>().apply{
        set("")
    }


    private val _action = MutableLiveData<String>()
    val action: LiveData<String> = _action

    val categoryType = ObservableField<String>().apply{
        set("BNS")
    }

    init{
        getUsers()
    }

    fun setSearchQuery(s: String){
        searchQuery.set(s)
        filter()
    }

    private fun getUsers(){
        viewModelScope.launch{
            try {
                userList = userRepo.getUsers()
                //userRepo.resetArchive()
                filter()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to get users $e")
            }
        }
    }

    fun filter(){
        _filteredList.value = if(searchQuery.get()?.isEmpty()!!){
            userList
        }else{
            userList.filter{
                it.firstName.contains(searchQuery.get()!!, true)
            }
        }

        _filteredList.value = when(categoryType.get()){
            "Archive" -> _filteredList.value?.filter {it.archive == "Yes"}
            "BNS" -> _filteredList.value?.filter { it.user == "personnel" && it.archive != "Yes" && it.verified != "No"}
            "parent" -> _filteredList.value?.filter {it.user == "parent" && it.archive != "Yes" && it.verified != "No"}
            "Request for Deletion" -> _filteredList.value?.filter {it.deletionRequest == "true"}
            "Verify" -> _filteredList.value?.filter {it.verified == "No"}
            else -> _filteredList.value!!
        }

        setAction()

    }
    private fun setAction(){
        _action.value = when(categoryType.get()){
            "Request for Deletion"  -> "delete"
            "Archive" -> "unarchive"
            "Verify" -> "verify"
            else -> "archive"
        }
    }
}