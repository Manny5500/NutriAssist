package com.example.projectcontact.repository.user

import com.example.projectcontact.model.User

interface UserRepo {
    suspend fun updateImage(imageUrl : String, userId: String)
    suspend fun getUser(userId: String) : User
    suspend fun requestDeletion(userId: String)
    suspend fun unrequestDeletion(userId: String)
    suspend fun getUsers() : List<User>
    suspend fun grantRequestDeletion(userId: String)
    suspend fun unarchiveUser(userId: String)
    suspend fun archiveUser(userId: String)
    suspend fun verifyUser(userId: String)
    suspend fun resetArchive()
}