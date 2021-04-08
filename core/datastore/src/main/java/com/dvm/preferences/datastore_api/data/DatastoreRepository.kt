package com.dvm.preferences.datastore_api.data

interface DatastoreRepository {
    suspend fun isAuthorized(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
}