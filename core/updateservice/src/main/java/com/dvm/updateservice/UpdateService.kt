package com.dvm.updateservice

import com.dvm.db.db_api.data.*
import com.dvm.db.db_api.data.models.Recommended
import com.dvm.network.network_api.api.MenuApi
import com.dvm.network.network_api.api.OrderApi
import com.dvm.network.network_api.api.ProfileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateService @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val reviewRepository: ReviewRepository,
    private val profileRepository: ProfileRepository,
    private val orderRepository: OrderRepository,
    private val menuApi: MenuApi,
    private val profileApi: ProfileApi,
    private val orderApi: OrderApi,
) {

    suspend fun update() = withContext(Dispatchers.IO) {
        try {
            val categories = async { menuApi.getCategories() }
            val recommended = async { menuApi.getRecommended() }
            val profile = async { profileApi.getProfile() }
            val dishes = menuApi.getDishes()
            val reviews =
                dishes.map {
                    async { menuApi.getReviews(dishId = it.id) }
                }

            updateOrders()
            categoryRepository.insertCategories(categories.await().map { it.toDbEntity() })
            dishRepository.insertDishes(dishes.filter { it.active }.map { it.toDbEntity() })
            dishRepository.insertRecommended(recommended.await().map { Recommended(it) })
            reviewRepository.insertReviews(reviews.awaitAll().flatten().map { it.toDbEntity() })
            profileRepository.updateProfile(profile.await().toDbEntity())
        } catch (exception: Exception) {
            // TODO
        }
    }

    suspend fun updateOrders() = withContext(Dispatchers.IO) {
        val statuses = async { orderApi.getStatuses() }
        val orders = orderApi.getOrders()
        // delete inactive
        orderRepository.insertOrderStatuses(statuses.await().map { it.toDbEntity() })
        orderRepository.insertOrders(orders.map { it.toDbEntity() })
        orderRepository.insertOrderItems(
            orders.map { order ->
                order.items.map {
                    it.toDbEntity(order.id)
                }
            }.flatten()
        )
    }
}