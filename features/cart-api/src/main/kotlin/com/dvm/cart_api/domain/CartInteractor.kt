package com.dvm.cart_api.domain

import com.dvm.cart_api.domain.model.Address
import com.dvm.cart_api.domain.model.Cart
import com.dvm.cart_api.domain.model.CartDishItem
import com.dvm.cart_api.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartInteractor {
    fun cartItems(): Flow<List<CartDishItem>>
    fun totalQuantity(): Flow<Int?>
    suspend fun getDishCount(): Int
    suspend fun addToCart(cartItem: CartItem)
    suspend fun addToCart(cartItems: List<CartItem>)
    suspend fun addPiece(dishId: String)
    suspend fun removePiece(dishId: String)
    suspend fun clearCart()

    suspend fun getCart(token: String): Cart

    suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): Cart

    suspend fun checkInput(address: String): Address

    suspend fun checkCoordinates(
        latitude: Double,
        longitude: Double
    ): Address
}