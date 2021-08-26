package com.dvm.db.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dvm.db.api.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ProfileDao {

    @Query(
        """
            SELECT *
            FROM profile
        """
    )
    abstract fun profile(): Flow<Profile?>

    @Transaction
    open suspend fun updateProfile(profile: Profile){
        deleteProfile()
        insertProfile(profile)
    }

    @Query(
        """
            DELETE FROM profile
        """
    )
    abstract fun deleteProfile()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProfile(profile: Profile)
}