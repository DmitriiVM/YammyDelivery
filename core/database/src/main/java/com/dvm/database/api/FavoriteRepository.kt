package com.dvm.database.api

interface FavoriteRepository {
    suspend fun isFavorite(dishId: String): Boolean
    suspend fun getFavorites(): List<String>
    suspend fun addToFavorite(dishId: String)
    suspend fun addListToFavorite(favorites: List<String>)
    suspend fun deleteFromFavorite(dishId: String)
    suspend fun deleteFavorites()
}