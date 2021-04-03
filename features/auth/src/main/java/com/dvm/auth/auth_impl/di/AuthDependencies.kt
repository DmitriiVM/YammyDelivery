package com.dvm.auth.auth_impl.di

import com.dvm.module_injector.BaseDependencies
import com.dvm.network.network_api.services.AuthService
import com.dvm.preferences.datastore_api.data.DatastoreRepository

interface AuthDependencies: BaseDependencies {
    fun service(): AuthService
    fun datastore(): DatastoreRepository
}