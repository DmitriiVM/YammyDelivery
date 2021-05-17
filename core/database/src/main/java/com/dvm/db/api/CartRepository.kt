package com.dvm.db.api

import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.CartItemDetails
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun cartItems(): Flow<List<CartItemDetails>>
    fun totalQuantity(): Flow<Int?>
    suspend fun getDishCount(): Int
    suspend fun addToCart(cartItem: CartItem)
    suspend fun addToCart(cartItems: List<CartItem>)
    suspend fun addPiece(dishId: String)
    suspend fun removePiece(dishId: String)
    suspend fun clearCart()
}