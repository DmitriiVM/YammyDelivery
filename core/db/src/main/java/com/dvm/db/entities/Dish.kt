package com.dvm.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)