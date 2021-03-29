package com.dvm.menu.di

import com.dvm.db.db_api.data.*

interface MenuDependencies {
    fun categoryDao(): CategoryRepository
    fun dishDao(): DishRepository
    fun favoriteDao(): FavoriteRepository
    fun cartDao(): CartRepository
    fun reviewDao(): ReviewRepository
}