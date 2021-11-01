package com.dvm.preferences.impl

import com.dvm.preferences.api.DatastoreRepository
import org.koin.dsl.module

val datastoreModule = module {

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