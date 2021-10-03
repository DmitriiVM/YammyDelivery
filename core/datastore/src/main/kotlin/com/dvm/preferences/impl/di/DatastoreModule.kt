package com.dvm.preferences.impl.di

import com.dvm.preferences.api.DatastoreRepository
import com.dvm.preferences.impl.DataStore
import com.dvm.preferences.impl.DefaultDatastoreRepository
import org.koin.dsl.module

val datastoreModule = module{

    factory<DatastoreRepository> {
        DefaultDatastoreRepository(
            dataStore = get()
        )
    }

    single {
        DataStore(
            context = get()
        )
    }

}