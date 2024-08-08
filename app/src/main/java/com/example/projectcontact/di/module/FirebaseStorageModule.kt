package com.example.projectcontact.di.module

import com.example.projectcontact.di.scope.RootReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseStorageModule {

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    @RootReference
    fun provideStorageReference(storage: FirebaseStorage): StorageReference{
        return storage.reference
    }

}

