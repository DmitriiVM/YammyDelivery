package com.dvm.db.db_impl.di

import com.dvm.db.db_api.di.DatabaseApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DatabaseModule::class],
    dependencies = [DatabaseDependencies::class]
)
internal interface DatabaseComponent: DatabaseApi {

    companion object {
        fun initAndGet(dependencies: DatabaseDependencies): DatabaseComponent =
            DaggerDatabaseComponent.builder()
                .databaseDependencies(dependencies)
                .build()
    }
}