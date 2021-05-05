package com.dvm.preferences.api.di

import com.dvm.preferences.api.data.DatastoreRepository

interface DatastoreApi {
    fun repository(): DatastoreRepository
}