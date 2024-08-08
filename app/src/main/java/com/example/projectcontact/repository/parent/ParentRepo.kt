package com.example.projectcontact.repository.parent

import com.example.projectcontact.model.Parent

interface ParentRepo {
    suspend fun getParents():List<Parent>
    suspend fun saveParent(parent: Parent)
    suspend fun getParents(barangay:String): List<Parent>
}