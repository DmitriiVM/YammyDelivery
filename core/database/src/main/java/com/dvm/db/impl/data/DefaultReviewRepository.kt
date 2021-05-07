package com.dvm.db.impl.data

import com.dvm.db.api.ReviewRepository
import com.dvm.db.api.models.Review
import com.dvm.db.impl.data.dao.ReviewDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
): ReviewRepository {

    override suspend fun insertReviews(reviews: List<Review>)  = withContext(Dispatchers.IO){
        reviewDao.insertReviews(reviews)
    }
}
