package com.dvm.database.impl.repositories

import com.dvm.database.Hint
import com.dvm.database.HintQueries
import com.dvm.database.api.HintRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultHintRepository(
    private val hintQueries: HintQueries
) : HintRepository {

    override fun hints(): Flow<List<String>> =
        hintQueries
            .hints()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun hintCount(): Int =
        withContext(Dispatchers.IO) {
            hintQueries
                .hintCount()
                .executeAsOne()
                .toInt()
        }

    override suspend fun insert(hint: Hint) =
        withContext(Dispatchers.IO) {
            hintQueries.insert(hint)
        }

    override suspend fun delete(hint: String) =
        withContext(Dispatchers.IO) {
            hintQueries.delete(hint)
        }

    override suspend fun deleteOldest() =
        withContext(Dispatchers.IO) {
            hintQueries.deleteOldest()
        }
}