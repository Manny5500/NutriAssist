package com.example.projectcontact.di.module

import com.example.projectcontact.di.scope.BarangayCollection
import com.example.projectcontact.di.scope.ChildCollection
import com.example.projectcontact.di.scope.HistoryCollection
import com.example.projectcontact.di.scope.ParentCollection
import com.example.projectcontact.di.scope.UserCollection
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {
    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    @ChildCollection
    fun provideChildCollection(firestore: FirebaseFirestore):CollectionReference{
        return firestore.collection("child")
    }

    @Provides
    @Singleton
    @HistoryCollection
    fun provideHistoryCollection(firestore: FirebaseFirestore):CollectionReference{
        return firestore.collection("children_historical")
    }

    @Provides
    @Singleton
    @ParentCollection
    fun provideParentCollection(firestore: FirebaseFirestore):CollectionReference{
        return firestore.collection("parent")
    }

    @Provides
    @Singleton
    @BarangayCollection
    fun provideBarangayCollection(firestore: FirebaseFirestore) : CollectionReference{
        return firestore.collection("barangay")
    }


    @Provides
    @Singleton
    @UserCollection
    fun provideUserCollection(firestore: FirebaseFirestore) : CollectionReference{
        return firestore.collection("users")
    }

}