package com.dvm.database.impl.repositories

import com.dvm.database.ReviewQueries
import com.dvm.database.api.ReviewRepository
import com.dvm.network.api.response.ReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultReviewRepository(
    private val reviewsQueries: ReviewQueries
) : ReviewRepository {

    override suspend fun insertReviews(reviews: List<ReviewResponse>) =
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
