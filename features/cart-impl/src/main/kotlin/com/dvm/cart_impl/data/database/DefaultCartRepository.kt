package com.dvm.cart_impl.data.database

import com.dvm.cart_api.domain.model.CartItem
import com.dvm.cart_impl.data.mappers.toCartItemEntity
import com.dvm.cart_impl.data.mappers.toCartItems
import com.dvm.cart_impl.domain.CartRepository
import com.dvm.cartdatabase.CartQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DefaultCartRepository(
    private val cartQueries: CartQueries
) : CartRepository {

    override fun cartItems(): Flow<List<CartItem>> =
        cartQueries
            .cartItemDetails()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { it.toCartItems() }

    override fun totalQuantity(): Flow<Int> =
        cartQueries
            .totalQuantity(
                mapper = { it ?: 0 }
            )
            .asFlow()
            .mapToOne(Dispatchers.IO)

    override suspend fun getDishCount(): Int =
        withContext(Dispatchers.IO) {
            cartQueries
                .cartCount()
                .executeAsOne()
                .toInt()
        }

    override suspend fun addToCart(cartItem: CartItem) =
        withContext(Dispatchers.IO) {
            cartQueries.insert(cartItem.toCartItemEntity())
        }

    override suspend fun addToCart(cartItems: List<CartItem>) =
        withContext(Dispatchers.IO) {
            cartQueries.transaction {
                cartItems.forEach {
                    cartQueries.insert(it.toCartItemEntity())
                }
            }
        }

    override suspend fun clearCart() =
        withContext(Dispatchers.IO) {
            cartQueries.clearCart()
        }

    override suspend fun addPiece(dishId: String) =
        withContext(Dispatchers.IO) {
            cartQueries.addPiece(dishId)
        }

    override suspend fun removePiece(dishId: String) =
        withContext(Dispatchers.IO) {
            val dishQuantity = cartQueries
                .dishQuantity(dishId)
                .executeAsOne()
            if (dishQuantity <= 1) {
                cartQueries.removeDishFromCart(dishId)
            } else {
                cartQueries.removeDishPiece(dishId)
            }
        }
}