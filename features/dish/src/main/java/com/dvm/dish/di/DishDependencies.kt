package com.dvm.dish.di

import com.dvm.db.dao.*

interface DishDependencies {
    fun categoryDao(): CategoryDao
    fun dishDao(): DishDao
    fun favoriteDao(): FavoriteDao
    fun cartDao(): CartDao
    fun reviewDao(): ReviewDao
}