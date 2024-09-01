package com.example.projectcontact.repository.barangay


import com.example.projectcontact.di.scope.BarangayCollection
import com.example.projectcontact.di.scope.IoDispatcher
import com.example.projectcontact.model.Barangay
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BarangayRepoImpl @Inject constructor(
    @BarangayCollection private val barangayCollection: CollectionReference,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BarangayRepo {
    override suspend fun getBarangay(barangay: String): Barangay = withContext(ioDispatcher){
        val snapshot = barangayCollection
            .document(barangay)
            .get()
            .await()
        snapshot.toObject(Barangay::class.java)!!
    }

    override suspend fun getBarangays(): List<Barangay> = withContext(ioDispatcher) {
        val snapshot = barangayCollection
            .whereNotEqualTo("identifier", "All Beneficiaries")
            .get()
            .await()
        snapshot.documents.map{
            it.toObject(Barangay::class.java)!!.copy(
                barangay = it.id
            )
        }
    }
}