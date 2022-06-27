package com.dvm.cart_impl.domain

import com.dvm.cart_api.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

internal interface CartRepository {
    fun cartItems(): Flow<List<CartItem>>
    fun totalQuantity(): Flow<Int>
    suspend fun getDishCount(): Int
    suspend fun addToCart(cartItem: CartItem)
    suspend fun addToCart(cartItems: List<CartItem>)
    suspend fun addPiece(dishId: String)
    suspend fun removePiece(dishId: String)
    suspend fun clearCart()
}