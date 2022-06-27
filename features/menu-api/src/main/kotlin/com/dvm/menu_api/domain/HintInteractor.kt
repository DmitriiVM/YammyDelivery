package com.dvm.menu_api.domain

import com.dvm.menu_api.domain.model.Hint
import kotlinx.coroutines.flow.Flow

interface HintInteractor {
    fun hints(): Flow<List<String>>
    suspend fun delete(hint: String)
    suspend fun saveHint(hint: Hint)
}