package com.dvm.db.impl.dao

import androidx.room.*
import com.dvm.db.api.models.CategoryDish
import com.dvm.db.api.models.Dish
import com.dvm.db.api.models.DishDetails
import com.dvm.db.api.models.Recommended
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DishDao {

    @Transaction
    @Query(
        """
            SELECT *
            FROM category_dish
            WHERE id = :dishId
        """
    )
    fun dish(dishId: String): Flow<DishDetails>

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
            FROM category_dish
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
            FROM category_dish
            WHERE oldPrice > price
        """
    )
    suspend fun getSpecialOffers(): List<CategoryDish>

    @Query(
        """
            SELECT *
            FROM category_dish
            JOIN recommended ON id = recommended.dishId
        """
    )
    fun recommended(): Flow<List<CategoryDish>>

    @Query(
        """
            SELECT *
            FROM category_dish
            WHERE rating > 3.5
            LIMIT 10
        """
    )
    fun best(): Flow<List<CategoryDish>>

    @Query(
        """
            SELECT *
            FROM category_dish
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
            FROM category_dish
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

