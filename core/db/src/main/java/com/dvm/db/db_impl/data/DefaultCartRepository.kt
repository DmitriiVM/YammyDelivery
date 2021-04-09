package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.models.Cart
import com.dvm.db.db_impl.data.dao.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCartRepository @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun addToCart(dishId: String, quantity: Int)= withContext(Dispatchers.IO) {
        cartDao.addToCart(Cart(dishId, quantity))
    }
}