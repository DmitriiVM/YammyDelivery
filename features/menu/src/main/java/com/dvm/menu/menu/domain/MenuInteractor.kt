package com.dvm.menu.menu.domain

import android.content.Context
import com.dvm.db.AppDatabase
import com.dvm.menu.menu.domain.model.MenuItem

class MenuInteractor {
    suspend fun getParentCategories(context: Context): List<MenuItem> {
        val categoryDao = AppDatabase.getDb(context).categoryDao()
        val dishDao = AppDatabase.getDb(context).dishDao()

        val hasSpecialOffers = dishDao.hasSpecialOffers()

        return mutableListOf<MenuItem>().apply {
            if (hasSpecialOffers) add(MenuItem.SpecialOffer)
            addAll(
                categoryDao
                    .getParentCategories()
                    .map { category ->
                        category.icon?.let {
                            MenuItem.Item(category.name, it)
                        } ?: MenuItem.Default(category.name)
                    }
            )
        }
    }
}