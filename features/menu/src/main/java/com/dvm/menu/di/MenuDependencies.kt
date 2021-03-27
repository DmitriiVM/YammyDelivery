package com.dvm.menu.di

import com.dvm.db.dao.*

interface MenuDependencies {
    fun categoryDao(): CategoryDao
    fun dishDao(): DishDao
    fun favoriteDao(): FavoriteDao
    fun cartDao(): CartDao
    fun reviewDao(): ReviewDao
}