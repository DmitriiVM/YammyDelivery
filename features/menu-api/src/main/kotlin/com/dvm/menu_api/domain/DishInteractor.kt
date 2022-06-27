package com.dvm.menu_api.domain

import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.DishDetails
import kotlinx.coroutines.flow.Flow

interface DishInteractor {
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
    suspend fun getRecommended(): List<String>
    suspend fun updateRecommended()
    suspend fun updateDishes(lastUpdateTime: Long)
    suspend fun getDishes(
        lastUpdateTime: Long?
    ): List<Dish>
}