package com.dvm.db.db_api.data

interface FavoriteRepository {
    suspend fun isFavorite(dishId: String): Boolean
    suspend fun addToFavorite(dishId: String)
    suspend fun deleteFromFavorite(dishId: String)
}