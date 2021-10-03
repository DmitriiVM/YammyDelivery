package com.dvm.network

import com.dvm.network.api.response.TokenResponse
import com.dvm.network.impl.request.RefreshTokenRequest
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.createFullToken
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal fun HttpClientConfig<*>.Authenticator(
    datastore: DatastoreRepository
) =
    install(
        key = "authenticator",
        block = {
            receivePipeline.intercept(HttpReceivePipeline.After) { response ->
                if (response.status.value != 401) {
                    proceedWith(response)
                } else {
                    val refreshToken = requireNotNull(datastore.getRefreshToken())
                    val tokenResponse: TokenResponse = post("auth/refresh") {
                        body = RefreshTokenRequest(refreshToken)
                    }
                    val newToken = tokenResponse.accessToken

                    datastore.saveAccessToken(newToken)
                    val newResponse = request<HttpResponse> {
                        takeFrom(response.request)
                        headers.remove(HttpHeaders.Authorization)
                        header(HttpHeaders.Authorization, createFullToken(newToken))
                    }
                    proceedWith(newResponse)
                }
            }
        }
    )