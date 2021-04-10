package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import kotlinx.coroutines.flow.Flow

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
    fun getDish(dishId: String): Flow<DishDetails>

    @Query(
        """
        SELECT
            *,
            EXISTS(
                SELECT 1
                FROM favorite
                WHERE favorite.dishId = dish.id
            ) as isFavorite
        FROM dish
        WHERE category IS :category
        AND active = 1
    """
    )
    suspend fun getDishes(category: String): List<CategoryDish>

    @Query(
        """
        SELECT
            *,
            EXISTS(
                SELECT 1
                FROM favorite
                WHERE favorite.dishId = dish.id
            ) as isFavorite
        FROM dish
        WHERE dish.name LIKE '%' || :query || '%'
        AND dish.active = 1
        """
    )
    fun search(query: String): Flow<List<CategoryDish>>

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
        SELECT
            *,
            EXISTS(
                SELECT 1
                FROM favorite
                WHERE favorite.dishId = dish.id
            ) as isFavorite
        FROM dish
        WHERE oldPrice > price
        AND active = 1
    """
    )
    suspend fun getSpecialOffers(): List<CategoryDish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<Dish>)
}

