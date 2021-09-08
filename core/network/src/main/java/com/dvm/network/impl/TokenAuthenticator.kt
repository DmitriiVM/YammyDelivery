package com.dvm.network.impl

import com.dvm.network.api.AuthApi
import com.dvm.preferences.api.DatastoreRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class TokenAuthenticator(
    private val datastore: DatastoreRepository,
    private val authApi: Lazy<AuthApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null

        val newAccessToken = runBlocking {
            val refreshToken = datastore.getRefreshToken()
            refreshToken ?: return@runBlocking null

            val tokens = try {
                authApi.value.refreshToken(refreshToken)
            } catch (exception: Exception) {
                return@runBlocking null
            }
            datastore.saveAccessToken(tokens.accessToken)

            tokens.accessToken
        }
        newAccessToken ?: return null

        return response.request
            .newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}