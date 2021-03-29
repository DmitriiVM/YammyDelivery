package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails

interface DishRepository {
    suspend fun getDish(dishId: String): DishDetails
    suspend fun getDishes(category: String): List<Dish>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<Dish>
    suspend fun insertDishes(dishes: List<Dish>)
}