package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getDish(dishId: String): Flow<DishDetails>
    fun search(query: String): Flow<List<CategoryDish>>
    suspend fun getDishes(category: String): List<CategoryDish>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<CategoryDish>
    suspend fun insertDishes(dishes: List<Dish>)
}