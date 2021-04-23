package com.dvm.db.db_api.data.models

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "dish")
data class Dish(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long
)

@DatabaseView(
    """
        SELECT
            *,
            EXISTS(
                SELECT 1
                FROM favorite
                WHERE favorite.dishId = dish.id
            ) as isFavorite
        FROM dish
    """
)
data class CategoryDish(
    val id: String,
    val name: String,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val likes: Int,
    val isFavorite: Boolean,
)

data class DishDetails(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val oldPrice: Int,
    val price: Int,
    val rating: Double,
    val isFavorite: Boolean,
    @Relation(
        parentColumn = "id",
        entityColumn = "dishId"
    )
    val reviews: List<Review>
)