package com.dvm.menu

import android.content.Context
import com.dvm.db.AppDatabase

object Graph {

    val categoryDao by lazy {
        database.categoryDao()
    }

    val dishDao by lazy {
        database.dishDao()
    }

    val favoriteDao by lazy {
        database.favoriteDao()
    }

    val cartDao by lazy {
        database.cartDao()
    }

    private lateinit var database: AppDatabase

    fun provide(context: Context){
        database = AppDatabase.getDb(context.applicationContext)
    }
}