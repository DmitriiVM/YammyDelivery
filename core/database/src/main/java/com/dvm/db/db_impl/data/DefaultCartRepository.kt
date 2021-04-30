package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.models.CartItem
import com.dvm.db.db_api.data.models.CartItemDetails
import com.dvm.db.db_impl.data.dao.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCartRepository @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun cartItems(): Flow<List<CartItemDetails>> = cartDao.cartItems()

    override suspend fun getCount()= withContext(Dispatchers.IO) {
        cartDao.getCount()
    }

    override suspend fun addToCart(dishId: String, quantity: Int)= withContext(Dispatchers.IO) {
        cartDao.addToCart(CartItem(dishId, quantity))
    }

    override suspend fun addToCart(cart: List<Pair<String, Int>>) {
        cartDao.addToCart(
            cart.map { CartItem(it.first, it.second) }
        )
    }

    override suspend fun removeItem(dishId: String) {
        cartDao.removeItem(dishId)
    }

    override suspend fun clearCart() = withContext(Dispatchers.IO) {
        cartDao.clearCart()
    }
}