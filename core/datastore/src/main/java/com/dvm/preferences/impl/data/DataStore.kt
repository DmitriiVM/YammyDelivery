package com.dvm.preferences.impl.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "yammy_datastore")

    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val UPDATE_ERROR = booleanPreferencesKey("update_error")

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

    suspend fun isAuthorized(): Boolean = !get(ACCESS_TOKEN).isNullOrBlank()

    suspend fun getAccessToken(): String? = get(ACCESS_TOKEN)?.let { "Bearer $it" }

    suspend fun saveAccessToken(accessToken: String) {
        save(ACCESS_TOKEN, accessToken)
    }

    suspend fun getRefreshToken(): String? = get(REFRESH_TOKEN)

    suspend fun saveRefreshToken(refreshToken: String) {
        save(REFRESH_TOKEN, refreshToken)
    }

    suspend fun setUpdateError(error: Boolean) {
        save(UPDATE_ERROR, error)
    }

    suspend fun isUpdateError(): Boolean = get(UPDATE_ERROR) ?: false
}