package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails

@Dao
internal interface DishDao {

    @Query(
        """
        SELECT
            *,
            EXISTS(
                SELECT 1
                FROM favorite
                WHERE favorite.dishId = :dishId
            ) as isFavorite
        FROM dish
        WHERE id = :dishId
        AND active = 1
    """
    )
    suspend fun getDish(dishId: String): DishDetails

    @Query(
        """
        SELECT *
        FROM dish
        WHERE category IS :category
        AND active = 1
    """
    )
    suspend fun getDishes(category: String): List<Dish>

    @Query(
        """
            SELECT EXISTS(
                SELECT 1
                FROM dish
                WHERE oldPrice > 0
            )

        """
    )
    suspend fun hasSpecialOffers(): Boolean

    @Query(
        """
        SELECT *
        FROM dish
        WHERE oldPrice > price
        AND active = 1
    """
    )
    suspend fun getSpecialOffers(): List<Dish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<Dish>)
}

