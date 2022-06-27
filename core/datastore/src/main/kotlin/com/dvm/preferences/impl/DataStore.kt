package com.dvm.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

internal class DataStore(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "yammy_datastore")

    private val accessToken = stringPreferencesKey("access_token")
    private val refreshToken = stringPreferencesKey("refresh_token")
    private val updateError = booleanPreferencesKey("update_error")
    private val lastUpdateTime = longPreferencesKey("last_update_time")

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private suspend fun <T> get(key: Preferences.Key<T>): T? =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key]
            }
            .first()

    fun authorized(): Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                !preferences[accessToken].isNullOrEmpty()
            }

    suspend fun isAuthorized(): Boolean = !get(accessToken).isNullOrBlank()

    suspend fun getAccessToken(): String? = get(accessToken)

    suspend fun saveAccessToken(accessToken: String) {
        save(this.accessToken, accessToken)
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { settings ->
            settings.remove(accessToken)
        }
    }

    suspend fun getRefreshToken(): String? = get(refreshToken)

    suspend fun saveRefreshToken(refreshToken: String) {
        save(this.refreshToken, refreshToken)
    }

    suspend fun setUpdateError(error: Boolean) {
        save(updateError, error)
    }

    suspend fun isUpdateError(): Boolean = get(updateError) ?: false

    suspend fun setLastUpdateTime(time: Long) {
        save(lastUpdateTime, time)
    }

    // suspend fun getLastUpdateTime(): Long = get(LAST_UPDATE_TIME) ?: 0
    // the server is for testing purposes and all data has the same update time
    // so getting update time is senseless
    suspend fun getLastUpdateTime(): Long = 0
}