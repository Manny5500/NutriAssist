package com.example.projectcontact.ui.bns.bns_account

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontact.model.User
import com.example.projectcontact.repository.storage.StorageRepo
import com.example.projectcontact.repository.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BNSAccountViewModel @Inject constructor(
    private val storageRepo: StorageRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _user = MutableLiveData<User>().apply{
        value = User()
    }
    val user : LiveData<User> = _user


    fun uploadImage(imageUri: Uri, userId : String){
        viewModelScope.launch{
            try{
                val downloadUrl = storageRepo.uploadImage("helloworld.jpg", imageUri )
                userRepo.updateImage(downloadUrl, userId)

                getUser(userId)
            }catch(e:Exception){
                Log.e("MyApp","Failed to upload image $e")
            }
        }
    }

    fun getUser(userId: String){
        viewModelScope.launch{
            try{
                _user.value = userRepo.getUser(userId)
            }catch(e:Exception){
                Log.e("MyApp", "Failed to get user $e")
            }

        }
    }

    fun requestForDeletion(userId: String){
        viewModelScope.launch {
            try{
                userRepo.requestDeletion(userId)

                getUser(userId)
            }catch (e: Exception){
                Log.e("MyApp", "Failed to request for deletion $e")
            }
        }
    }

    fun unrequestForDeletion(userId: String){
        viewModelScope.launch {
            try{
                userRepo.unrequestDeletion(userId)

                getUser(userId)
            }catch (e: Exception){
                Log.e("MyApp", "Failed to request for deletion $e")
            }
        }
    }

}