package com.dvm.db.api

import com.dvm.db.api.models.CategoryDish
import com.dvm.db.api.models.Dish
import com.dvm.db.api.models.DishDetails
import com.dvm.db.api.models.Recommended
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun dish(dishId: String): Flow<DishDetails>
    fun search(query: String): Flow<List<CategoryDish>>
    fun recommended(): Flow<List<CategoryDish>>
    fun best(): Flow<List<CategoryDish>>
    fun popular(): Flow<List<CategoryDish>>
    fun favorite(): Flow<List<CategoryDish>>
    suspend fun getDishes(category: String): Flow<List<CategoryDish>>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<CategoryDish>
    suspend fun insertDishes(dishes: List<Dish>)
    suspend fun insertRecommended(dishIds: List<Recommended>)
}