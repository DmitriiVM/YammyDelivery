package com.dvm.menu_impl.domain.interactor

import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.DishRepository
import com.dvm.menu_api.domain.ReviewInteractor
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.DishDetails
import com.dvm.menu_impl.domain.api.MenuApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultDishInteractor(
    private val dishRepository: DishRepository,
    private val reviewInteractor: ReviewInteractor,
    private val menuApi: MenuApi
) : DishInteractor {

    override suspend fun updateRecommended() = withContext(Dispatchers.IO) {
        val recommended = menuApi.getRecommended()
        dishRepository.insertRecommended(recommended)
    }

    override suspend fun updateDishes(lastUpdateTime: Long) = withContext(Dispatchers.IO) {
        val dishes = menuApi.getDishes(lastUpdateTime)
        dishRepository.insertDishes(dishes)

        val reviews = dishes.map {
            async {
                menuApi.getReviews(
                    dishId = it.id,
                    lastUpdateTime = lastUpdateTime
                )

            }
        }
            .awaitAll()
            .flatten()

        reviewInteractor.insertReviews(reviews)
    }

    override fun dish(dishId: String): Flow<DishDetails> =
        dishRepository.dish(dishId)

    override fun search(query: String): Flow<List<CardDish>> =
        dishRepository.search(query)

    override fun recommended(): Flow<List<CardDish>> =
        dishRepository.recommended()

    override fun best(): Flow<List<CardDish>> =
        dishRepository.best()

    override fun popular(): Flow<List<CardDish>> =
        dishRepository.popular()

    override fun favorite(): Flow<List<CardDish>> =
        dishRepository.favorite()

    override suspend fun getDishes(category: String): Flow<List<CardDish>> =
        dishRepository.getDishes(category)

    override suspend fun hasSpecialOffers(): Boolean =
        dishRepository.hasSpecialOffers()

    override suspend fun getSpecialOffers(): List<CardDish> =
        dishRepository.getSpecialOffers()

    override suspend fun insertDishes(dishes: List<Dish>) {
        dishRepository.insertDishes(dishes)
    }

    override suspend fun insertRecommended(dishIds: List<String>) {
        dishRepository.insertRecommended(dishIds)
    }

    override suspend fun getRecommended(): List<String> =
        menuApi.getRecommended()

    override suspend fun getDishes(lastUpdateTime: Long?): List<Dish> =
        menuApi.getDishes(lastUpdateTime)
}