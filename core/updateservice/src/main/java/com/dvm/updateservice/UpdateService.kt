package com.dvm.updateservice

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateService {


    // TODO exception
    suspend fun update(context: Context) = withContext(Dispatchers.IO) {

//        val api = ApiService.getNetworkService()
//        val db = DatabaseComponentHolder.getApi()
//        val categoryDao = db.categoryRepository()
//        val dishDao = db.dishRepository()
//        val reviewDao = db.reviewRepository()
//
//        val categories = async { api.getCategories(limit = 1000) }
//        val dishes = api.getDishes(limit = 1000)
//        dishes.forEach {
////            Log.d("mmm", "UpdateService :  update --  $it")
//        }
//
//        val reviews =
//            dishes
//                .map {
//                    async {
//                        try {
//                            api.getReviews(dishId = it.id)
//                        } catch (e: Exception) {
//                            emptyList()
//                        }
//                    }
//                }
//                .map { it.await() }
//                .flatten()
//
//        categoryDao.insertCategories(categories.await().map { it.toDbEntity() })
//        dishDao.insertDishes(dishes.map { it.toDbEntity() })
//        reviewDao.insertReviews(reviews.map { it.toDbEntity() })
    }

}