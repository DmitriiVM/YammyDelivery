package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getDish(dishId: String): Flow<DishDetails>
    suspend fun getDishes(category: String): List<Dish>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<Dish>
    suspend fun insertDishes(dishes: List<Dish>)
}