package com.dvm.preferences.datastore_impl.di

import android.content.Context
import com.dvm.module_injector.BaseDependencies

interface DatastoreDependencies: BaseDependencies {
    fun context(): Context
}