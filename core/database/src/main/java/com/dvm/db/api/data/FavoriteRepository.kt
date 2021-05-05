package com.dvm.db.api.data

interface FavoriteRepository {
    suspend fun isFavorite(dishId: String): Boolean
    suspend fun addToFavorite(dishId: String)
    suspend fun deleteFromFavorite(dishId: String)
    suspend fun getFavorites(): List<String>
    suspend fun addListToFavorite(favorites: List<String>)
}