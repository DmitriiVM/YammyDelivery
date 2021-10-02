package com.dvm.database.impl.dao

import androidx.room.*
import com.dvm.database.api.models.Favorite

@Dao
internal interface FavoriteDao {

    @Query(
        """
            SELECT 1
            FROM favorite
            WHERE dishId = :dishId
        """
    )
    fun isFavorite(dishId: String): Boolean

    @Query(
        """
            SELECT dishId
            FROM favorite
        """
    )
    suspend fun getFavorites(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(favorites: List<Favorite>)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query(
        """
            DELETE FROM favorite
        """
    )
    suspend fun deleteFavorites()
}