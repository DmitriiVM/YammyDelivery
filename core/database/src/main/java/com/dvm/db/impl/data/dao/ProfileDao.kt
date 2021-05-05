package com.dvm.db.impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.api.data.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProfileDao {

    @Query(
        """
            SELECT *
            FROM profile
        """
    )
    fun profile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Query(
        """
            DELETE FROM profile
        """
    )
    fun deleteProfile()
}