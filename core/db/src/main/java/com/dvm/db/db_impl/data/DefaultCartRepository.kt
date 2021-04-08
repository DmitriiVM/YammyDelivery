package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.models.Cart
import com.dvm.db.db_impl.data.dao.CartDao
import javax.inject.Inject

internal class DefaultCartRepository @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun addToCart(dishId: String, quantity: Int){
        cartDao.addToCart(Cart(dishId, quantity))
    }
}