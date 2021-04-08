package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.db.db_api.data.models.Favorite
import com.dvm.db.db_impl.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultFavoriteRepository @Inject constructor(
    private val database: AppDatabase
) : FavoriteRepository {

    override suspend fun isFavorite(dishId: String): Boolean = withContext(Dispatchers.IO) {
        database.favoriteDao().isFavorite(dishId)
    }

    override suspend fun addToFavorite(dishId: String) = withContext(Dispatchers.IO) {
        database.favoriteDao().insert(Favorite(dishId))
    }

    override suspend fun deleteFromFavorite(dishId: String) = withContext(Dispatchers.IO) {
        database.favoriteDao().delete(Favorite(dishId))
    }
}