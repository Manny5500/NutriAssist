package com.example.projectcontact.repository.user

import android.util.Log
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.di.scope.UserCollection
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @UserCollection private val userCollection: CollectionReference
) : UserRepo {

    override suspend fun updateImage(imageUrl: String, userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection.document(userId)
                    .update("imageUrl", imageUrl)
                    .await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to update user image$e")
            }
        }
    }

    override suspend fun getUser(userId: String): User =
        withContext(ioDispatcher){
        val snapshot = userCollection
            .document(userId)
            .get()
            .await()
        snapshot.toObject(User::class.java)!!
    }

    override suspend fun requestDeletion(userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection
                    .document(userId)
                    .update("deletionRequest", "true")
                    .await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to request deletion $e")
            }
        }
    }

    override suspend fun unrequestDeletion(userId: String) {
        withContext(ioDispatcher){
            try{
                userCollection
                    .document(userId)
                    .update(mapOf(
                        "deletionRequest" to "false",
                        "readyToDelete" to "false"))
                    .await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to unrequest deletion $e")
            }
        }
    }

    override suspend fun getUsers(): List<User> =
    withContext(ioDispatcher){
        val snapshot = userCollection.get().await()
        snapshot.documents.map{
            it.toObject(User::class.java)!!.copy(
                id = it.id
            )
        }
    }

    override suspend fun grantRequestDeletion(userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection
                    .document(userId)
                    .update("readyToDelete", "true")
                    .await()
            }catch (e: Exception){
                Log.e("MyApp", "Failed to grant request of deletion of the user $e")
            }
        }
    }

    override suspend fun unarchiveUser(userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection
                    .document(userId)
                    .update("archive", "No")
                    .await()
            }catch (e: Exception){
                Log.e("MyApp", "Failed to unarchive the user $e")
            }
        }
    }

    override suspend fun archiveUser(userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection
                    .document(userId)
                    .update("archive", "Yes")
                    .await()
            }catch (e: Exception){
                Log.e("MyApp", "Failed to archive the user $e")
            }
        }
    }

    override suspend fun verifyUser(userId: String) {
        withContext(ioDispatcher){
            try {
                userCollection
                    .document(userId)
                    .update("verified", "Yes")
                    .await()
            }catch (e: Exception){
                Log.e("MyApp", "Failed to verify the user $e")
            }
        }
    }

    //This function is just for resetting the value of a field
    //Useful from turning all null fields into primitive type instead of manual configuration
    //Must not be implemented in production level
    override suspend fun resetArchive() {
        withContext(ioDispatcher){
            try {
                val batch = FirebaseFirestore.getInstance().batch()
                val querySnapshot = FirebaseFirestore.getInstance().collection("users").get().await()
                for(document in querySnapshot.documents){
                    val docRef = document.reference
                    batch.update(docRef,"id", "")
                    batch.update(docRef, "imageUrl", "")
                    batch.update(docRef, "deletionRequest", "")

                }
                batch.commit().await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to update the archive $e")
            }
        }
    }

}