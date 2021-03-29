package com.dvm.dish.dish_impl.di

import com.dvm.db.db_api.data.*
import com.dvm.module_injector.BaseDependencies

interface DishDependencies: BaseDependencies {
    fun categoryDao(): CategoryRepository
    fun dishDao(): DishRepository
    fun favoriteDao(): FavoriteRepository
    fun cartDao(): CartRepository
    fun reviewDao(): ReviewRepository
}