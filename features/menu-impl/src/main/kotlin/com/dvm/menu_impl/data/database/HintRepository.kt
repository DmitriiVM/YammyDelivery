package com.dvm.menu_impl.data.database

import com.dvm.menu_api.domain.model.Hint
import com.dvm.menu_impl.data.mappers.toHintEntity
import com.dvm.menu_impl.domain.repository.HintRepository
import com.dvm.menudatabase.HintQueries
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
            hintQueries.insert(hint.toHintEntity())
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