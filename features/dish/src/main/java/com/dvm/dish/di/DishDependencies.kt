package com.dvm.dish.di

import com.dvm.db.db_api.data.*

interface DishDependencies {
    fun categoryDao(): CategoryRepository
    fun dishDao(): DishRepository
    fun favoriteDao(): FavoriteRepository
    fun cartDao(): CartRepository
    fun reviewDao(): ReviewRepository
}