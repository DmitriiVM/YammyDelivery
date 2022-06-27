package com.dvm.menu_api.domain

import com.dvm.menu_api.domain.model.Favorite

interface FavoriteInteractor {
    suspend fun getFavorites(): List<String>
    suspend fun addListToFavorite(favorites: List<String>)
    suspend fun deleteFavorites()
    suspend fun updateFavorites(lastUpdateTime: Long)
    suspend fun toggleFavorite(dishId: String)

    suspend fun getFavorite(
        token: String,
        lastUpdateTime: Long?
    ): List<Favorite>
}