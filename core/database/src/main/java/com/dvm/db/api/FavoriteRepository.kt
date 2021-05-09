package com.dvm.db.api

import com.dvm.db.api.models.Favorite

interface FavoriteRepository {
    suspend fun isFavorite(dishId: String): Boolean
    suspend fun getFavorites(): List<String>
    suspend fun addToFavorite(dishId: String)
    suspend fun addListToFavorite(favorites: List<Favorite>)
    suspend fun deleteFromFavorite(dishId: String)
}