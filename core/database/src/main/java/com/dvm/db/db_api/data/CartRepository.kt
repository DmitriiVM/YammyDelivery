package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun cartItems(): Flow<List<CartItem>>
    suspend fun addToCart(dishId: String, quantity: Int)
    suspend fun clearCart()
    suspend fun removeItem(dishId: String)
}