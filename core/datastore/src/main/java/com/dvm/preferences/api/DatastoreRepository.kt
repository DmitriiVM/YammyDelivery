package com.dvm.preferences.api

interface DatastoreRepository {
    suspend fun isAuthorized(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun setUpdateError(error: Boolean)
    suspend fun isUpdateError(): Boolean
    suspend fun setLastUpdateTime(time: Long)
    suspend fun getLastUpdateTime(): Long
}