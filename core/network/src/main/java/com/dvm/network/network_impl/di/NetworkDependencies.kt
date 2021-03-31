package com.dvm.network.network_impl.di

import com.dvm.module_injector.BaseDependencies
import com.dvm.preferences.datastore_api.data.DatastoreRepository

interface NetworkDependencies: BaseDependencies {
    fun datastore(): DatastoreRepository
}