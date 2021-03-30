package com.dvm.preferences.datastore_impl.di

import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.preferences.datastore_impl.data.DefaultDatastoreRepository
import dagger.Binds
import dagger.Module

@Module
internal interface DatastoreModule {
    @Binds
    fun provideDatastoreRepository(repository: DefaultDatastoreRepository): DatastoreRepository
}