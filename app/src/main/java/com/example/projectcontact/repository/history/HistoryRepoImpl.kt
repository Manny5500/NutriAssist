package com.example.projectcontact.repository.history

import android.util.Log
import com.example.projectcontact.di.scope.HistoryCollection
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.model.History
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepoImpl@Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @HistoryCollection private val historyCollection: CollectionReference
):HistoryRepo {

    override suspend fun getHistory(fullName:String): List<History> = withContext(ioDispatcher){
        return@withContext try{
            val snapshot = historyCollection
                .document(fullName)
                .collection("dates")
                .get()
                .await()
            snapshot.documents.map{
                it.toObject(History::class.java)!!.copy(
                    id = it.id
                )
            }
        }catch (e:Exception){
            Log.e("MyApp", "Failed to get History $e")
            emptyList<History>()
        }

    }

    override suspend fun updateHistory(history: History, fullName:String) {
        try{
           historyCollection
                .document(fullName)
                .collection("dates")
                .document(history.id)
                .update(mapOf(
                    "height" to history.height,
                    "weight" to history.weight,
                    "statusdb" to history.statusdb,
                    "status" to history.status,
                    "sex" to history.sex))
                .await()
        }catch (e:Exception){
            Log.e("MyApp", "Failed to update History $e")
        }

    }

    override suspend fun saveHistory(history: History, fullName: String) {
        try{
            historyCollection
                .document(fullName)
                .collection("dates")
                .document(history.id)
                .set(history)
                .await()
        }catch (e:Exception){
            Log.e("MyApp", "Failed to saveHistory $e")
        }
    }

}