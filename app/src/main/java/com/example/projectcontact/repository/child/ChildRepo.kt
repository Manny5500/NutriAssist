package com.example.projectcontact.repository.child

import com.example.projectcontact.model.Child
import com.google.firebase.Timestamp

interface ChildRepo {
    suspend fun getChildren() : List<Child>
    suspend fun updateChild(child:Child)
    suspend fun deleteChild(childId:String)
    suspend fun addChild(child:Child)
    suspend fun getChildren(gmail:String, from:Timestamp, to:Timestamp) : List<Child>
    suspend fun getChildren(from: Timestamp, to:Timestamp) : List<Child>
}