package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.ReviewRepository
import com.dvm.db.db_api.data.models.Review
import com.dvm.db.db_impl.data.dao.ReviewDao
import javax.inject.Inject

internal class DefaultReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
): ReviewRepository{

    override suspend fun insertReviews(reviews: List<Review>)  =
        reviewDao.insertReviews(reviews)
}