package com.dvm.updateservice

import android.content.Context
import com.dvm.db.AppDatabase
import com.dvm.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class UpdateService {


    // TODO exception
    suspend fun update(context: Context) = withContext(Dispatchers.IO) {

        val api = ApiService.getNetworkService()
        val db = AppDatabase.getDb(context.applicationContext)
        val categoryDao = db.categoryDao()
        val dishDao = db.dishDao()
        val reviewDao = db.reviewDao()

        val categories = async { api.getCategories(limit = 1000) }
        val dishes = api.getDishes(limit = 1000)
        dishes.forEach { 
//            Log.d("mmm", "UpdateService :  update --  $it")
        }

        val reviews =
            dishes
                .map {
                    async {
                        try {
                            api.getReviews(dishId = it.id)
                        } catch (e: Exception) {
                            emptyList()
                        }
                    }
                }
                .map { it.await() }
                .flatten()

        categoryDao.insertCategories(categories.await().map { it.toDbEntity() })
        dishDao.insertDishes(dishes.map { it.toDbEntity() })
        reviewDao.insertReviews(reviews.map { it.toDbEntity() })
    }

}