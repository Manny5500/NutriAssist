package com.example.projectcontact.repository.child

import android.util.Log
import com.example.projectcontact.di.scope.ChildCollection
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.model.Child
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChildRepoImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ChildCollection private val childCollection: CollectionReference
):ChildRepo {

    override suspend fun getChildren(): List<Child> = withContext(ioDispatcher){
        val snapshot = childCollection.get().await()
        snapshot.documents.map{
            it.toObject(Child::class.java)!!.copy(
                id = it.id
            )
        }
    }

    override suspend fun getChildren(gmail: String, from:Timestamp, to:Timestamp): List<Child>
    = withContext(ioDispatcher) {
        val snapshot = childCollection
            .whereEqualTo("gmail", gmail)
            .whereGreaterThanOrEqualTo("dateAdded", from)
            .whereLessThanOrEqualTo("dateAdded", to)
            .get()
            .await()
        snapshot.documents.map{
            it.toObject(Child::class.java)!!.copy(
                id = it.id
            )
        }
    }

    override suspend fun getChildren(from: Timestamp, to: Timestamp): List<Child>
    = withContext(ioDispatcher){
        val snapshot = childCollection
            .whereGreaterThanOrEqualTo("dateAdded", from)
            .whereLessThanOrEqualTo("dateAdded", to)
            .get()
            .await()
        snapshot.documents.map{
            it.toObject(Child::class.java)!!.copy(
                id = it.id
            )
        }
    }

    override suspend fun updateChild(child: Child){
      withContext(ioDispatcher){
          try {
              childCollection.document(child.id)
                  .update(mapOf(
                      "childFirstName" to child.childFirstName,
                      "childMiddleName" to child.childMiddleName,
                      "childLastName" to child.childLastName,
                      "parentFirstName" to child.parentFirstName,
                      "parentMiddleName" to child.parentMiddleName,
                      "parentLastName" to child.parentLastName,
                      "sex" to child.sex,
                      "expectedDate" to child.expectedDate,
                      "birthDate" to child.birthDate,
                      "weight" to child.weight,
                      "height" to child.height,
                      "sitio" to child.sitio,
                      "houseNumber" to child.houseNumber,
                      "phoneNumber" to child.phoneNumber,
                      "gmail" to child.gmail,
                      "statusdb" to child.statusdb,
                      "status" to child.status
                  ))
                  .await()
          }catch (e:Exception){
              Log.e("MyApp", "Failed update to child$e")
          }
      }
    }

    override suspend fun deleteChild(childId: String) {
        withContext(ioDispatcher){
            try {
                childCollection.document(childId).delete().await()
            }catch(e:Exception){
                Log.e("MyApp", "Failed delete to child$e")
            }
        }
    }

    override suspend fun addChild(child: Child) {
        withContext(ioDispatcher){
            try{
                childCollection.add(child).await()
            }catch (e: Exception){
                Log.e("MyApp", "Failed add to child$e")
            }
        }
    }

}