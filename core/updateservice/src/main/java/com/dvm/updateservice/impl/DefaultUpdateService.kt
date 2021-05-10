package com.dvm.updateservice.impl

import com.dvm.db.api.*
import com.dvm.db.api.models.Recommended
import com.dvm.network.api.MenuApi
import com.dvm.network.api.OrderApi
import com.dvm.network.api.ProfileApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.updateservice.api.UpdateService
import com.dvm.updateservice.toDbEntity
import kotlinx.coroutines.*
import javax.inject.Inject

internal class DefaultUpdateService @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val reviewRepository: ReviewRepository,
    private val profileRepository: ProfileRepository,
    private val orderRepository: OrderRepository,
    private val menuApi: MenuApi,
    private val profileApi: ProfileApi,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository
) : UpdateService {

    override suspend fun update() = withContext(Dispatchers.IO) {
        val lastUpdateTime = datastore.getLastUpdateTime()

        val categories = async {
            menuApi.getCategories(lastUpdateTime)
        }
        val recommended = async { menuApi.getRecommended() }
        val profile = datastore.getAccessToken()?.let {
            async { profileApi.getProfile(it) }
        }
        val dishes = menuApi.getDishes(lastUpdateTime)
        val reviews = dishes.map {
            async {
                menuApi.getReviews(
                    dishId = it.id,
                    lastUpdateTime = lastUpdateTime
                )
            }
        }

        categoryRepository.insertCategories(
            categories
                .await()
                .map { it.toDbEntity() }
        )
        dishRepository.insertDishes(
            dishes
                .filter { it.active }
                .map { it.toDbEntity() }
        )
        dishRepository.insertRecommended(
            recommended
                .await()
                .map { Recommended(it) }
        )
        reviewRepository.insertReviews(
            reviews
                .awaitAll()
                .flatten()
                .map { it.toDbEntity() }
        )
        profile?.await()?.toDbEntity()?.let {
            profileRepository.updateProfile(it)
        }
        updateOrders()
        datastore.setLastUpdateTime(System.currentTimeMillis())
    }

    override suspend fun updateOrders() = withContext(Dispatchers.IO) {
        val token = datastore.getAccessToken() ?: return@withContext
        val lastUpdateTime = datastore.getLastUpdateTime()

        val statuses = async { orderApi.getStatuses(lastUpdateTime) }
        val orders = orderApi.getOrders(token, lastUpdateTime)
        with(orderRepository) {
            deleteInactiveOrders()
            insertOrders(
                orders
                    .map { it.toDbEntity() }
            )
            insertOrderItems(
                orders.map { order ->
                    order.items.map {
                        it.toDbEntity(order.id)
                    }
                }.flatten()
            )
            insertOrderStatuses(
                statuses
                    .await()
                    .map { it.toDbEntity() }
            )
        }
    }
}