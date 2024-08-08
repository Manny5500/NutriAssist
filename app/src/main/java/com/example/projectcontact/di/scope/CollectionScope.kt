package com.example.projectcontact.di.scope

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ChildCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class HistoryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ParentCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BarangayCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserCollection