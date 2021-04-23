package com.dvm.db.db_impl.data.dao

import androidx.room.*
import com.dvm.db.db_api.data.models.Favorite

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}