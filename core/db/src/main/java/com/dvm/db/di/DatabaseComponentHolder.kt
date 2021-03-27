package com.dvm.db.di

object DatabaseComponentHolder {

    private var databaseComponent: DatabaseComponent? = null

    fun init(dependencies: DatabaseDependencies){
        databaseComponent = DaggerDatabaseComponent.builder().databaseDependencies(dependencies).build()
    }

    // temp
    fun get() = databaseComponent!!
}