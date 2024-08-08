package com.example.projectcontact.repository.parent

import android.util.Log
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.di.scope.ParentCollection
import com.example.projectcontact.model.Parent
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ParentRepoImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ParentCollection private val parentCollection: CollectionReference
) : ParentRepo {

    override suspend fun getParents(): List<Parent>  = withContext(ioDispatcher){
        val snapshot = parentCollection.get().await()
        snapshot.documents.map {
            it.toObject(Parent::class.java)!!
        }
    }

    override suspend fun getParents(barangay: String): List<Parent> = withContext(ioDispatcher){
        val snapshot = parentCollection
            .whereEqualTo("barangay",barangay)
            .get()
            .await()
        snapshot.documents.map{
            it.toObject(Parent::class.java)!!
        }
    }

    override suspend fun saveParent(parent: Parent) {
        withContext(ioDispatcher){
            try {
                parentCollection.document(parent.gmail).set(parent).await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to save tempEmail $e")
            }
        }
    }

}