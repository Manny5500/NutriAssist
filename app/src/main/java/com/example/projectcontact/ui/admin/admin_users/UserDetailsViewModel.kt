package com.example.projectcontact.ui.admin.admin_users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.repository.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    fun archiveUser(id: String){
        viewModelScope.launch{
            try {
                userRepo.archiveUser(id)
            }catch(e : Exception){
                Log.e("MyApp", "Failed to archive the user $e")
            }
        }
    }

    fun grantDeletionRequest(id: String){
        viewModelScope.launch {
            try {
                userRepo.unrequestDeletion(id)
            }catch (e: Exception){
                Log.e("MyApp", "Failed to grant the user's deletion requeset $e")
            }
        }
    }

    fun removeDeletionReequest(id: String){
        viewModelScope.launch {
            try {
                userRepo.unrequestDeletion(id)
            }catch(e: Exception){
                Log.e("MyApp", "Failed to unrequest the deletion $e")
            }
        }
    }

    fun unarchiveUser(id:String){
        viewModelScope.launch {
            try {
                userRepo.unarchiveUser(id)
            }catch(e:Exception){
                Log.e("MyApp", "Failed to unarchive the user $e")
            }
        }
    }

    fun verifyUser(id:String?){
        viewModelScope.launch {
            try {
                if (id != null) {
                    userRepo.verifyUser(id)
                }
            }catch(e:Exception){
                Log.e("MyApp","Failed to verfiy the user $e")
            }
        }
    }

}