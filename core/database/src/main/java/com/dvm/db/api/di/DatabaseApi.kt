package com.dvm.db.api.di

import com.dvm.db.api.data.*

interface DatabaseApi{
    fun categoryRepository(): CategoryRepository
    fun dishRepository(): DishRepository
    fun reviewRepository(): ReviewRepository
    fun favoriteRepository(): FavoriteRepository
    fun cartRepository(): CartRepository
}