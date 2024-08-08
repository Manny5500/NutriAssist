package com.example.projectcontact.repository.storage

import android.net.Uri

interface StorageRepo {
    suspend fun uploadImage(fileName:String, uri: Uri):String
}