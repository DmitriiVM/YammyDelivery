package com.dvm.db.impl.repositories

import com.dvm.db.api.ReviewRepository
import com.dvm.db.api.models.Review
import com.dvm.db.impl.dao.ReviewDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultReviewRepository(
    private val reviewDao: ReviewDao
) : ReviewRepository {

    override suspend fun insertReviews(reviews: List<Review>) =
        withContext(Dispatchers.IO) {
            reviewDao.insertReviews(reviews)
        }
}
