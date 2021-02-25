package com.dvm.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)

}