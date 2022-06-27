package com.dvm.updateservice.impl

import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.FavoriteInteractor
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.updateservice.api.UpdateService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DefaultUpdateService(
    private val categoryInteractor: CategoryInteractor,
    private val dishInteractor: DishInteractor,
    private val profileInteractor: ProfileInteractor,
    private val orderInteractor: OrderInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val datastore: DatastoreRepository
) : UpdateService {

    override suspend fun updateAll() = withContext(Dispatchers.IO) {
        val lastUpdateTime = datastore.getLastUpdateTime()

        launch { profileInteractor.updateProfile() }
        launch { updateOrders() }
        launch { categoryInteractor.updateCategories(lastUpdateTime) }
        launch { dishInteractor.updateRecommended() }
        launch { dishInteractor.updateDishes(lastUpdateTime) }

        datastore.setLastUpdateTime(System.currentTimeMillis())
    }

    override suspend fun updateOrders() = withContext(Dispatchers.IO) {
        orderInteractor.updateOrders()
    }

    override suspend fun syncFavorites() = withContext(Dispatchers.IO) {
        val lastUpdateTime = datastore.getLastUpdateTime()
        favoriteInteractor.updateFavorites(lastUpdateTime)
    }
}