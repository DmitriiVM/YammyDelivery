package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.db.db_api.data.models.Dish
import com.dvm.db.db_api.data.models.DishDetails
import com.dvm.db.db_impl.data.dao.DishDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultDishRepository @Inject constructor(
    private val dishDao: DishDao
) : DishRepository{

    override fun getDish(dishId: String): Flow<DishDetails> =
        dishDao.getDish(dishId)

    override fun search(query: String): Flow<List<CategoryDish>> = dishDao.search(query)

    override suspend fun getDishes(category: String): List<CategoryDish> = withContext(Dispatchers.IO) {
        dishDao.getDishes(category)
    }

    override suspend fun hasSpecialOffers(): Boolean  = withContext(Dispatchers.IO) {
        dishDao.hasSpecialOffers()
    }

    override suspend fun getSpecialOffers(): List<CategoryDish>  = withContext(Dispatchers.IO) {
        dishDao.getSpecialOffers()
    }

    override suspend fun insertDishes(dishes: List<Dish>)  = withContext(Dispatchers.IO) {
        dishDao.insertDishes(dishes)
    }
}