package com.dvm.database.impl.repositories

import com.dvm.database.api.HintRepository
import com.dvm.database.api.models.Hint
import com.dvm.database.impl.dao.HintDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultHintRepository @Inject constructor(
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