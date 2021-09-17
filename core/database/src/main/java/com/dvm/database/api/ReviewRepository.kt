package com.dvm.database.api

import com.dvm.network.api.response.ReviewResponse

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<ReviewResponse>)
}