package com.dvm.database.api

import com.dvm.database.Dish
import com.dvm.database.api.models.CardDish
import com.dvm.database.api.models.DishDetails
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun dish(dishId: String): Flow<DishDetails>
    fun search(query: String): Flow<List<CardDish>>
    fun recommended(): Flow<List<CardDish>>
    fun best(): Flow<List<CardDish>>
    fun popular(): Flow<List<CardDish>>
    fun favorite(): Flow<List<CardDish>>
    suspend fun getDishes(category: String): Flow<List<CardDish>>
    suspend fun hasSpecialOffers(): Boolean
    suspend fun getSpecialOffers(): List<CardDish>
    suspend fun insertDishes(dishes: List<Dish>)
    suspend fun insertRecommended(dishIds: List<String>)
}