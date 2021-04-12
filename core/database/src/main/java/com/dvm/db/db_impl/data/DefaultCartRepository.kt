package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.models.Cart
import com.dvm.db.db_api.data.models.CartItem
import com.dvm.db.db_impl.data.dao.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCartRepository @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun cartItems(): Flow<List<CartItem>> = cartDao.cartItems()

    override suspend fun addToCart(dishId: String, quantity: Int)= withContext(Dispatchers.IO) {
        cartDao.addToCart(Cart(dishId, quantity))
    }

    override suspend fun removeItem(dishId: String) {
        cartDao.removeItem(dishId)
    }

    override suspend fun clearCart() = withContext(Dispatchers.IO) {
        cartDao.clearCart()
    }
}