package com.dvm.updateservice

import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.ReviewRepository
import com.dvm.db.db_api.data.models.Recommended
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
        val recommended = menuApi.getRecommended()
        val dishes = menuApi.getDishes()
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
        dishRepository.insertDishes(dishes.filter { it.active }.map { it.toDbEntity() })
        dishRepository.insertRecommended(recommended.map { Recommended(it) })
        reviewRepository.insertReviews(reviews.map { it.toDbEntity() })
    }
}