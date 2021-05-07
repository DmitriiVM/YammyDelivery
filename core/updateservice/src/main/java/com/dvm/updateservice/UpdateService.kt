package com.dvm.updateservice

import android.util.Log
import com.dvm.db.api.*
import com.dvm.db.api.models.Recommended
import com.dvm.network.api.MenuApi
import com.dvm.network.api.OrderApi
import com.dvm.network.api.ProfileApi
import com.dvm.preferences.api.DatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    private val datastore: DatastoreRepository
) {

    suspend fun update() = withContext(Dispatchers.IO) {
        try {
            val categories = async { menuApi.getCategories() }
            val recommended = async { menuApi.getRecommended() }
            val profile = async { profileApi.getProfile() }

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

            updateOrders()
            categoryRepository.insertCategories(categories.await().map { it.toDbEntity() })
            dishRepository.insertDishes(dishes.filter { it.active }.map { it.toDbEntity() })
            dishRepository.insertRecommended(recommended.await().map { Recommended(it) })
            reviewRepository.insertReviews(reviews.map { it.toDbEntity() })
            profileRepository.updateProfile(profile.await().toDbEntity())

            datastore.setLastUpdateTime(System.currentTimeMillis())
        } catch (exception: Exception) {
            Log.d("mmm", "UpdateService :  update --  $exception")
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