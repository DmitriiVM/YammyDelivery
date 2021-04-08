package com.dvm.db.db_api.data

interface CartRepository {
    suspend fun addToCart(dishId: String, quantity: Int)
}