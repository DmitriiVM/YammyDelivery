package com.dvm.menu_impl.data.database

import com.dvm.menu_api.domain.DishRepository
import com.dvm.menu_api.domain.model.CardDish
import com.dvm.menu_api.domain.model.Dish
import com.dvm.menu_api.domain.model.DishDetails
import com.dvm.menu_impl.data.mappers.toDishEntity
import com.dvm.menu_impl.data.mappers.toReview
import com.dvm.menudatabase.DishQueries
import com.dvm.menudatabase.RecommendedQueries
import com.dvm.menudatabase.ReviewQueries
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
                            reviews = reviews.map { it.toReview() },
                        )
                    }
            }

    override suspend fun getDish(dishId: String): DishDetails {
        val dish = dishQueries
            .dish(dishId)
            .executeAsOne()

        val reviews = reviewsQueries
            .reviews(dishId)
            .executeAsList()

        return DishDetails(
            id = dish.id,
            name = dish.name,
            description = dish.description,
            image = dish.image,
            oldPrice = dish.oldPrice,
            price = dish.price,
            rating = dish.rating,
            isFavorite = dish.isFavorite,
            reviews = reviews.map { it.toReview() },
        )
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
                    dishQueries.insert(it.toDishEntity())
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