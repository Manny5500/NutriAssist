package com.example.projectcontact.repository.user

import android.util.Log
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.di.scope.UserCollection
import com.example.projectcontact.model.User
import com.google.firebase.firestore.CollectionReference
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
                    .update("deletionRequest", "false")
                    .await()
            }catch (e:Exception){
                Log.e("MyApp", "Failed to unrequest deletion $e")
            }

        }
    }


}