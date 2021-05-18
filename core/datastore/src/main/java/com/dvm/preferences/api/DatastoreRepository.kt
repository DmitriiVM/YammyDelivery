package com.dvm.preferences.api

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    fun accessToken(): Flow<String?>
    fun authorized(): Flow<Boolean>
    suspend fun isAuthorized(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken()
    suspend fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun setUpdateError(error: Boolean)
    suspend fun isUpdateError(): Boolean
    suspend fun setLastUpdateTime(time: Long)
    suspend fun getLastUpdateTime(): Long
}