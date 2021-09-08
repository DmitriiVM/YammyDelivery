package com.dvm.db.impl.repositories

import com.dvm.db.api.HintRepository
import com.dvm.db.api.models.Hint
import com.dvm.db.impl.dao.HintDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultHintRepository(
    private val hintDao: HintDao
) : HintRepository {

    override fun hints(): Flow<List<String>> = hintDao.hints()

    override suspend fun hintCount(): Int =
        withContext(Dispatchers.IO) {
            hintDao.hintCount()
        }

    override suspend fun insert(hint: Hint) =
        withContext(Dispatchers.IO) {
            hintDao.insert(hint)
        }

    override suspend fun delete(hint: String) =
        withContext(Dispatchers.IO) {
            hintDao.delete(hint)
        }

    override suspend fun deleteOldest() =
        withContext(Dispatchers.IO) {
            hintDao.deleteOldest()
        }
}