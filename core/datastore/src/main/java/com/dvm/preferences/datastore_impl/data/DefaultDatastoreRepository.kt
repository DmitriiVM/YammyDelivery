package com.dvm.preferences.datastore_impl.data

import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultDatastoreRepository @Inject constructor(
    private val dataStore: DataStore
): DatastoreRepository {

    override suspend fun isAuthorized(): Boolean = dataStore.isAuthorized()

    override suspend fun getAccessToken(): String? = dataStore.getAccessToken()

    override suspend fun saveAccessToken(token: String) {
        dataStore.saveAccessToken(token)
    }

    override suspend fun getRefreshToken(): String? = dataStore.getRefreshToken()

    override suspend fun saveRefreshToken(token: String) {
        dataStore.saveRefreshToken(token)
    }

    override suspend fun setUpdateError(error: Boolean) {
        dataStore.setUpdateError(error)
    }

    override suspend fun isUpdateError(): Boolean = dataStore.isUpdateError()
}