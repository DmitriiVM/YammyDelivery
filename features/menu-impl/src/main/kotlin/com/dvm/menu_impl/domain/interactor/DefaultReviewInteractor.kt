package com.dvm.menu_impl.domain.interactor

import com.dvm.menu_api.domain.ReviewInteractor
import com.dvm.menu_api.domain.model.Review
import com.dvm.menu_impl.domain.api.MenuApi
import com.dvm.menu_impl.domain.repository.ReviewRepository

internal class DefaultReviewInteractor(
    private val reviewRepository: ReviewRepository,
    private val menuApi: MenuApi
) : ReviewInteractor {

    override suspend fun insertReviews(reviews: List<Review>) {
        reviewRepository.insertReviews(reviews)
    }

    override suspend fun getReviews(dishId: String, lastUpdateTime: Long?): List<Review> =
        menuApi.getReviews(dishId, lastUpdateTime)

    override suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    ) =
        menuApi.addReview(
            token = token,
            dishId = dishId,
            rating = rating,
            text = text
        )
}