package com.dvm.db.api

import com.dvm.db.api.models.Review

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<Review>)
}