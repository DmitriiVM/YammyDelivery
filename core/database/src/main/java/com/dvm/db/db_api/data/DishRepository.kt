package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_api.data.models.Recommended
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getDish(dishId: String): Flow<DishDetails>
    fun search(query: String): Flow<List<CategoryDish>>
    suspend fun getDishes(category: String): List<CategoryDish>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<CategoryDish>
    suspend fun insertDishes(dishes: List<Dish>)
    suspend fun insertRecommended(dishIds: List<Recommended>)
    fun recommended(): Flow<List<CategoryDish>>
    fun best(): Flow<List<CategoryDish>>
    fun popular(): Flow<List<CategoryDish>>
    fun favorite(): Flow<List<CategoryDish>>
}