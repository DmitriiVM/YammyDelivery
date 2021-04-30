package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.CartItemDetails
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun cartItems(): Flow<List<CartItemDetails>>
    suspend fun getCount(): Int
    suspend fun addToCart(dishId: String, quantity: Int)
    suspend fun addToCart(cart: List<Pair<String, Int>>)
    suspend fun clearCart()
    suspend fun removeItem(dishId: String)
}