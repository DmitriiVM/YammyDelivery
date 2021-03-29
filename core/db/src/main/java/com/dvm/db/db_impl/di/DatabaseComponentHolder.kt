package com.dvm.db.db_impl.di

import com.dvm.db.db_api.di.DatabaseApi
import com.dvm.module_injector.ComponentHolder

object DatabaseComponentHolder: ComponentHolder<DatabaseApi, DatabaseDependencies> {

    @Volatile
    private var databaseComponent: DatabaseComponent? = null

    override fun init(dependencies: DatabaseDependencies) {
        if (databaseComponent == null) {
            synchronized(this) {
                if (databaseComponent == null) {
                    databaseComponent = DatabaseComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun getApi() : DatabaseApi {
        checkNotNull(databaseComponent) { "DatabaseComponent is not initialized!" }
        return databaseComponent!!
    }
}