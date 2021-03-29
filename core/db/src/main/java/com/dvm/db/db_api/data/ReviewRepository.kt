package com.dvm.db.db_api.data

import com.dvm.db.db_api.data.models.Review

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<Review>)
}