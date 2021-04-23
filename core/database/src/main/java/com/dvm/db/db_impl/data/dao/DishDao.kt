package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_api.data.models.Recommended
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DishDao {

    @Query(
        """
            SELECT *
            FROM categorydish
            WHERE id = :dishId
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
        """
    )
    suspend fun getDishes(category: String): List<CategoryDish>

    @Query(
        """
            SELECT *
            FROM categorydish
            WHERE name LIKE '%' || :query || '%'
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
            SELECT *
            FROM categorydish
            WHERE oldPrice > price
        """
    )
    suspend fun getSpecialOffers(): List<CategoryDish>

    @Query(
        """
            SELECT *
            FROM categorydish
            JOIN recommended
            ON id = recommended.dishId
        """
    )
    fun recommended(): Flow<List<CategoryDish>>

    @Query(
        """
            SELECT *
            FROM categorydish
            WHERE rating > 3.5
            LIMIT 10
        """
    )
    fun best(): Flow<List<CategoryDish>>

    @Query(
        """
            SELECT *
            FROM categorydish
            WHERE id IN (
                SELECT id
                FROM dish                
                ORDER BY likes DESC
                LIMIT 10
            )
        """
    )
    fun popular(): Flow<List<CategoryDish>>

    @Query(
        """
            SELECT *
            FROM categorydish
            WHERE id IN (
                SELECT dishId
                FROM favorite
            )
        """
    )
    fun favorite(): Flow<List<CategoryDish>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecommended(dishIds: List<Recommended>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<Dish>)
}

