package com.dvm.db.db_api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart (
    @PrimaryKey
    val dishId: String,
    val quantity: Int,
)

data class CartItem(
    val dishId: String,
    val quantity: Int,
    val name: String,
    val image: String,
    val price: Int
)