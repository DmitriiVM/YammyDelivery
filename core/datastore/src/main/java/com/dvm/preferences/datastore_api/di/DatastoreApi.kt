package com.dvm.preferences.datastore_api.di

import com.dvm.preferences.datastore_api.data.DatastoreRepository

interface DatastoreApi {
    fun repository(): DatastoreRepository
}