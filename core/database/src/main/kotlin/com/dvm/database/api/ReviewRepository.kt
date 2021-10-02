package com.dvm.database.api

import com.dvm.database.api.models.Review

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<Review>)
}