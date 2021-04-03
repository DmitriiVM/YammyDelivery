package com.dvm.preferences.datastore_impl.di

import com.dvm.module_injector.ComponentHolder
import com.dvm.preferences.datastore_api.di.DatastoreApi

object DatastoreComponentHolder: ComponentHolder<DatastoreApi, DatastoreDependencies> {

    @Volatile
    private var datastoreComponent: DatastoreComponent? = null

    override lateinit var dependencies: () -> DatastoreDependencies

    override fun init() {
        if (datastoreComponent == null){
            synchronized(this){
                if (datastoreComponent == null){
                    datastoreComponent = DatastoreComponent.initAndGet(dependencies())
                }
            }
        }
    }

    override fun destroy() {
        datastoreComponent = null
    }

    override fun getApi() : DatastoreApi {
        checkNotNull(datastoreComponent) { "DatastoreComponent is not initialized!" }
        return datastoreComponent!!
    }
}