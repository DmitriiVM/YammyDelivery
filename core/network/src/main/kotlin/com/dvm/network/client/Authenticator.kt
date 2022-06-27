package com.dvm.network.client

import com.dvm.network.model.RefreshTokenRequest
import com.dvm.network.model.TokenResponse
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.createFullToken
import io.ktor.client.HttpClientConfig
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.takeFrom
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders

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