package com.dvm.preferences.datastore_impl.di

import com.dvm.preferences.datastore_api.di.DatastoreApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DatastoreModule::class],
    dependencies = [DatastoreDependencies::class]
)
internal interface DatastoreComponent: DatastoreApi {

    companion object{
        fun initAndGet(dependencies: DatastoreDependencies): DatastoreComponent =
            DaggerDatastoreComponent.builder()
                .datastoreDependencies(dependencies)
                .build()
    }
}