package com.dvm.db.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class CartItem(
    @PrimaryKey
    val dishId: String,
    val quantity: Int,
)

data class CartItemDetails(
    val dishId: String,
    val quantity: Int,
    val name: String,
    val image: String,
    val price: Int
)