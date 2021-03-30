package com.dvm.preferences.datastore_api.di

import com.dvm.module_injector.BaseAPI
import com.dvm.preferences.datastore_api.data.DatastoreRepository

interface DatastoreApi : BaseAPI {
    fun repository(): DatastoreRepository
}