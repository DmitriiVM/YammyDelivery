package com.dvm.network.impl

import com.dvm.network.api.AuthApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.createFullToken
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
    private val datastore: DatastoreRepository,
    private val authApi: Lazy<AuthApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null

        val newAccessToken = runBlocking {
            val refreshToken = datastore.getRefreshToken()
            refreshToken ?: return@runBlocking null

            val tokens = try {
                authApi.get().refreshToken(refreshToken)
            } catch (exception: Exception) {
                return@runBlocking null
            }
            datastore.saveAccessToken(tokens.accessToken)

            tokens.accessToken
        }
        newAccessToken ?: return null

        return response.request
            .newBuilder()
            .header(ApiService.HEADER_AUTHORIZATION, createFullToken(newAccessToken))
            .build()
    }
}