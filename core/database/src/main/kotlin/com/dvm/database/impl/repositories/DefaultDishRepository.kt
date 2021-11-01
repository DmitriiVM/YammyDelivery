package com.dvm.database.impl.repositories

import com.dvm.database.Dish
import com.dvm.database.DishQueries
import com.dvm.database.RecommendedQueries
import com.dvm.database.ReviewQueries
import com.dvm.database.api.DishRepository
import com.dvm.database.api.models.CardDish
import com.dvm.database.api.models.DishDetails
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DefaultDishRepository(
    private val dishQueries: DishQueries,
    private val recommendedQueries: RecommendedQueries,
    private val reviewsQueries: ReviewQueries
) : DishRepository {

    @Suppress("MaxLineLength")
    private val cardDishMapper: (String, String, String?, String, Int, Int, Double, Int, Int, String, Boolean) -> CardDish =
        { id, name, description, image,
          oldPrice, price, rating, _,
          likes, _, isFavorite ->
            CardDish(
                id = id,
                name = name,
                description = description,
                image = image,
                oldPrice = oldPrice,
                price = price,
                rating = rating,
                likes = likes,
                isFavorite = isFavorite
            )
        }

    override fun dish(dishId: String): Flow<DishDetails> =
        dishQueries
            .dish(dishId)
            .asFlow()
            .mapToOne(Dispatchers.IO)
            .flatMapLatest { dish ->
                reviewsQueries
                    .reviews(dishId)
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .map { reviews ->
                        DishDetails(
                            id = dish.id,
                            name = dish.name,
                            description = dish.description,
                            image = dish.image,
                            oldPrice = dish.oldPrice,
                            price = dish.price,
                            rating = dish.rating,
                            isFavorite = dish.isFavorite,
                            reviews = reviews,
                        )
                    }
            }

    override fun search(query: String): Flow<List<CardDish>> =
        dishQueries
            .searchDish(
                query = query,
                mapper = cardDishMapper
            )
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun recommended(): Flow<List<CardDish>> =
        dishQueries
            .recommendedDishes(cardDishMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun best(): Flow<List<CardDish>> =
        dishQueries
            .bestDishes(cardDishMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun popular(): Flow<List<CardDish>> =
        dishQueries
            .popularDishes(cardDishMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun favorite(): Flow<List<CardDish>> =
        dishQueries
            .favoriteDishes(cardDishMapper)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun getDishes(category: String): Flow<List<CardDish>> =
        dishQueries
            .dishes(
                category = category,
                mapper = cardDishMapper
            )
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun hasSpecialOffers(): Boolean =
        withContext(Dispatchers.IO) {
            dishQueries
                .hasSpecialOffers()
                .executeAsOne()
        }

    override suspend fun getSpecialOffers(): List<CardDish> =
        withContext(Dispatchers.IO) {
            dishQueries
                .specialOffersDish(cardDishMapper)
                .executeAsList()
        }

    override suspend fun insertDishes(dishes: List<Dish>) =
        withContext(Dispatchers.IO) {
            dishQueries.transaction {
                dishes.forEach {
                    dishQueries.insert(it)
                }
            }
        }

    override suspend fun insertRecommended(dishIds: List<String>) =
        withContext(Dispatchers.IO) {
            recommendedQueries.transaction {
                dishIds.forEach {
                    recommendedQueries.insert(it)
                }
            }
        }
}