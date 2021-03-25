package com.dvm.db.dao

import androidx.room.*
import com.dvm.db.entities.Dish
import com.dvm.db.entities.Review

@Dao
interface DishDao {

    @Query(
        """
        SELECT *
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
                SELECT COUNT(oldPrice)
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


data class DishDetails(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    @Relation(
        parentColumn = "id",
        entityColumn = "dishId"
    )
    val review: List<Review>
) {

    val hasSpecialOffer: Boolean
        get() = oldPrice != 0 && oldPrice > price
}