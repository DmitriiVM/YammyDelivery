package com.dvm.network.client

import com.dvm.network.model.RefreshTokenRequest
import com.dvm.network.model.TokenResponse
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.createFullToken
import io.ktor.client.*
import io.ktor.client.call.*
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

                    val tokenResponse = post("auth/refresh") {
                        setBody(RefreshTokenRequest(refreshToken))
                    }
                        .body<TokenResponse>()

                    val newToken = tokenResponse.accessToken

                    datastore.saveAccessToken(newToken)

                    val newResponse = request {
                        takeFrom(response.request)
                        headers.remove(HttpHeaders.Authorization)
                        header(HttpHeaders.Authorization, createFullToken(newToken))
                    }
                        .body<HttpResponse>()

                    proceedWith(newResponse)
                }
            }
        }
    )