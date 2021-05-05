package com.dvm.db.impl.data

import com.dvm.db.api.data.DishRepository
import com.dvm.db.api.data.models.CategoryDish
import com.dvm.db.api.data.models.Dish
import com.dvm.db.api.data.models.DishDetails
import com.dvm.db.api.data.models.Recommended
import com.dvm.db.impl.data.dao.DishDao
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

    override fun recommended(): Flow<List<CategoryDish>> =  dishDao.recommended()

    override fun best(): Flow<List<CategoryDish>> = dishDao.best()

    override fun popular(): Flow<List<CategoryDish>> = dishDao.popular()

    override fun favorite(): Flow<List<CategoryDish>> = dishDao.favorite()

    override suspend fun insertRecommended(dishIds: List<Recommended>) {
        dishDao.insertRecommended(dishIds)
    }
}