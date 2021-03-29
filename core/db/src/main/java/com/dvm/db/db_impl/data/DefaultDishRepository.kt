package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_impl.AppDatabase
import javax.inject.Inject

internal class DefaultDishRepository @Inject constructor(
    private val database: AppDatabase
) : DishRepository{

    override suspend fun getDish(dishId: String): DishDetails =
        database.dishDao().getDish(dishId)

    override suspend fun getDishes(category: String): List<Dish> =
        database.dishDao().getDishes(category)

    override suspend fun hasSpecialOffers(): Boolean  =
        database.dishDao().hasSpecialOffers()

    override suspend fun getSpecialOffers(): List<Dish>  =
        database.dishDao().getSpecialOffers()

    override suspend fun insertDishes(dishes: List<Dish>)  =
        database.dishDao().insertDishes(dishes)
}