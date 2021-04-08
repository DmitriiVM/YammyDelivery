package com.dvm.updateservice

import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.ReviewRepository
import com.dvm.network.network_api.api.MenuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateService @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val reviewRepository: ReviewRepository,
    private val menuApi: MenuApi
) {


    // TODO exception
    suspend fun update() = withContext(Dispatchers.IO) {
        val categories = async { menuApi.getCategories() }
        val dishes = menuApi.getDishes()
        dishes.forEach {
//            Log.d("mmm", "UpdateService :  update --  $it")
        }

        val reviews =
            dishes
                .map {
                    async {
                        try {
                            menuApi.getReviews(dishId = it.id)
                        } catch (e: Exception) {
                            emptyList()
                        }
                    }
                }
                .map { it.await() }
                .flatten()

        categoryRepository.insertCategories(categories.await().map { it.toDbEntity() })
        dishRepository.insertDishes(dishes.map { it.toDbEntity() })
        reviewRepository.insertReviews(reviews.map { it.toDbEntity() })
    }
}