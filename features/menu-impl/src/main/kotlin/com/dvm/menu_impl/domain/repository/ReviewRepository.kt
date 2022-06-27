package com.dvm.menu_impl.domain.repository

import com.dvm.menu_api.domain.model.Review

interface ReviewRepository {
    suspend fun insertReviews(reviews: List<Review>)
}