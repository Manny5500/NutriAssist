package com.example.projectcontact.repository.storage

import android.net.Uri
import android.util.Log
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.di.scope.RootReference
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StorageRepoImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @RootReference private val rootReference: StorageReference
): StorageRepo {

    override suspend fun uploadImage(fileName: String, uri: Uri): String
    = withContext(ioDispatcher){
        try {
            val imageRef = rootReference.child("images/$fileName")
            imageRef.putFile(uri).await()
            imageRef.downloadUrl.await().toString()
        }catch (e:Exception){
            Log.e("MyApp", "Failed to upload image $e")
            ""
        }
    }
}