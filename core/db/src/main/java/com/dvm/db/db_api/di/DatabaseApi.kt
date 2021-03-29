package com.dvm.db.db_api.di

import com.dvm.db.db_api.data.*
import com.dvm.module_injector.BaseAPI

interface DatabaseApi: BaseAPI {
    fun categoryRepository(): CategoryRepository
    fun dishRepository(): DishRepository
    fun reviewRepository(): ReviewRepository
    fun favoriteRepository(): FavoriteRepository
    fun cartRepository(): CartRepository
}