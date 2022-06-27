package com.dvm.menu_impl.data.database

import com.dvm.menu_impl.domain.repository.FavoriteRepository
import com.dvm.menudatabase.FavoriteQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultFavoriteRepository(
    private val favoriteQueries: FavoriteQueries
) : FavoriteRepository {

    override suspend fun getFavorites(): List<String> =
        withContext(Dispatchers.IO) {
            favoriteQueries
                .favorites()
                .executeAsList()
        }

    override suspend fun isFavorite(dishId: String): Boolean =
        withContext(Dispatchers.IO) {
            favoriteQueries
                .isFavorite(dishId)
                .executeAsOne()
        }

    override suspend fun addToFavorite(dishId: String) =
        withContext(Dispatchers.IO) {
            favoriteQueries.insert(dishId)
        }

    override suspend fun deleteFromFavorite(dishId: String) =
        withContext(Dispatchers.IO) {
            favoriteQueries.delete(dishId)
        }

    override suspend fun addListToFavorite(favorites: List<String>) =
        withContext(Dispatchers.IO) {
            favoriteQueries.transaction {
                favorites.forEach {
                    favoriteQueries.insert(it)
                }
            }
        }

    override suspend fun deleteFavorites() =
        withContext(Dispatchers.IO) {
            favoriteQueries.deleteAll()
        }
}