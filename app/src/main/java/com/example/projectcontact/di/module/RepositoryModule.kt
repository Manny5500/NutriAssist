package com.example.projectcontact.di.module


import com.example.projectcontact.repository.barangay.BarangayRepo
import com.example.projectcontact.repository.barangay.BarangayRepoImpl
import com.example.projectcontact.repository.child.ChildRepo
import com.example.projectcontact.repository.child.ChildRepoImpl
import com.example.projectcontact.repository.history.HistoryRepo
import com.example.projectcontact.repository.history.HistoryRepoImpl
import com.example.projectcontact.repository.parent.ParentRepo
import com.example.projectcontact.repository.parent.ParentRepoImpl
import com.example.projectcontact.repository.storage.StorageRepo
import com.example.projectcontact.repository.storage.StorageRepoImpl
import com.example.projectcontact.repository.user.UserRepo
import com.example.projectcontact.repository.user.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChildRepo(repository: ChildRepoImpl): ChildRepo

    @Binds
    @Singleton
    abstract fun bindHistoryRepo(repository: HistoryRepoImpl): HistoryRepo

    @Binds
    @Singleton
    abstract fun bindTempEmailRepo(repository: ParentRepoImpl): ParentRepo

    @Binds
    @Singleton
    abstract fun bindBarangayRepo(repository: BarangayRepoImpl) : BarangayRepo

    @Binds
    @Singleton
    abstract fun bindStorageRepo(repoImpl: StorageRepoImpl): StorageRepo

    @Binds
    @Singleton
    abstract fun bindUserRepo(repository: UserRepoImpl) : UserRepo
}