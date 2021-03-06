package com.dvm.menu

import android.content.Context
import com.dvm.db.AppDatabase
import com.dvm.menu.category.domain.CategoryInteractor

object Graph {

    val categoryDao by lazy {
        database.categoryDao()
    }

    val dishDao by lazy {
        database.dishDao()
    }

    val interactor by lazy {
        CategoryInteractor()
    }

    private lateinit var database: AppDatabase

    fun provide(context: Context){
        database = AppDatabase.getDb(context.applicationContext)
    }
}