package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.Hint
import kotlinx.coroutines.flow.Flow

@Dao
internal interface HintDao {

    @Query(
        """
            SELECT `query`
            FROM hints
            ORDER BY date DESC
        """
    )
    fun hints(): Flow<List<String>>

    @Query(
        """
            SELECT COUNT()
            FROM hints
        """
    )
    suspend fun hintCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hint: Hint)

    @Query(
        """
            DELETE 
            FROM hints
            WHERE `query` = :hint
        """
    )
    suspend fun delete(hint: String)

    @Query(
        """
            DELETE
            FROM hints
            WHERE date = (
              SELECT MIN(date)
              FROM hints
            )
        """
    )
    suspend fun deleteOldest()
}