package com.dvm.menu_impl.domain.repository

import com.dvm.menu_api.domain.model.Hint
import kotlinx.coroutines.flow.Flow

internal interface HintRepository {
    fun hints(): Flow<List<String>>
    suspend fun hintCount(): Int
    suspend fun insert(hint: Hint)
    suspend fun delete(hint: String)
    suspend fun deleteOldest()
}