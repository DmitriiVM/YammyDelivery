package com.dvm.database.impl.repositories

import com.dvm.database.api.ReviewRepository
import com.dvm.database.api.models.Review
import com.dvm.database.impl.dao.ReviewDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
) : ReviewRepository {

    override suspend fun insertReviews(reviews: List<Review>) =
        withContext(Dispatchers.IO) {
            reviewDao.insertReviews(reviews)
        }
}
