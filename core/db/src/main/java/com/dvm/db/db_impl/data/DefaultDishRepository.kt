package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_impl.data.dao.DishDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultDishRepository @Inject constructor(
    private val dishDao: DishDao
) : DishRepository{

    override fun getDish(dishId: String): Flow<DishDetails> =
        dishDao.getDish(dishId)

    override suspend fun getDishes(category: String): List<Dish> =
        dishDao.getDishes(category)

    override suspend fun hasSpecialOffers(): Boolean  =
        dishDao.hasSpecialOffers()

    override suspend fun getSpecialOffers(): List<Dish>  =
        dishDao.getSpecialOffers()

    override suspend fun insertDishes(dishes: List<Dish>)  =
        dishDao.insertDishes(dishes)
}