package com.dvm.db.api.data

import com.dvm.db.api.data.models.Review

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<Review>)
}