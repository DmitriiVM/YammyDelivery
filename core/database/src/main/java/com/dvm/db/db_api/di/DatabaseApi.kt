package com.dvm.db.db_api.di

import com.dvm.db.db_api.data.*

interface DatabaseApi{
    fun categoryRepository(): CategoryRepository
    fun dishRepository(): DishRepository
    fun reviewRepository(): ReviewRepository
    fun favoriteRepository(): FavoriteRepository
    fun cartRepository(): CartRepository
}