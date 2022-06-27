package com.dvm.menu_api.domain

import com.dvm.menu_api.domain.model.Review

interface ReviewInteractor {
    suspend fun insertReviews(reviews: List<Review>)

    suspend fun getReviews(
        dishId: String,
        lastUpdateTime: Long?
    ): List<Review>

    suspend fun addReview(
        token: String,
        dishId: String,
        rating: Int,
        text: String
    )
}