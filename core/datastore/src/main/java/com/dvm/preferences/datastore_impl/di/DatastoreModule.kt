package com.dvm.preferences.datastore_impl.di

import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.preferences.datastore_impl.data.DefaultDatastoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DatastoreModule {
    @Binds
    fun provideDatastoreRepository(repository: DefaultDatastoreRepository): DatastoreRepository
}