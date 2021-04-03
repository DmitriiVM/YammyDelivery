package com.dvm.network.network_impl.di

import android.content.Context
import com.dvm.module_injector.BaseDependencies
import com.dvm.preferences.datastore_api.data.DatastoreRepository

interface NetworkDependencies: BaseDependencies {
    fun context(): Context
    fun datastore(): DatastoreRepository
}