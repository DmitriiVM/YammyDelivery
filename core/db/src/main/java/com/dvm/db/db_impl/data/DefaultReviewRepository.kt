package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.ReviewRepository
import com.dvm.db.db_api.data.models.Review
import com.dvm.db.db_impl.AppDatabase
import javax.inject.Inject

internal class DefaultReviewRepository @Inject constructor(
    private val database: AppDatabase
): ReviewRepository{

    override suspend fun insertReviews(reviews: List<Review>)  =
        database.reviewDao().insertReviews(reviews)
}