package com.dvm.menu_impl.data.database

import com.dvm.menu_api.domain.model.Review
import com.dvm.menu_impl.domain.repository.ReviewRepository
import com.dvm.menudatabase.ReviewQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultReviewRepository(
    private val reviewsQueries: ReviewQueries
) : ReviewRepository {

    override suspend fun insertReviews(reviews: List<Review>) =
        withContext(Dispatchers.IO) {
            reviewsQueries.transaction {
                reviews.forEach { review ->
                    reviewsQueries.insert(
                        dishId = review.dishId,
                        author = review.author,
                        date = review.date,
                        rating = review.rating,
                        text = review.text,
                        active = review.active,
                    )
                }
            }
        }
}