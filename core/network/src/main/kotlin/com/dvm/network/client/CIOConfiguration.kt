package com.dvm.network.client

import android.content.Context
import android.util.Log
import com.dvm.preferences.api.DatastoreRepository
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal object CIOConfiguration {

    operator fun invoke(
        context: Context,
        datastore: DatastoreRepository
    ): HttpClientConfig<CIOEngineConfig>.() -> Unit = {
        expectSuccess = false
        defaultRequest {
            host = "sandbox.skill-branch.ru"
            url {
                protocol = URLProtocol.HTTPS
            }
            contentType(ContentType.Application.Json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 10_0000
            requestTimeoutMillis = 10_0000
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("Ktor", "Ktor: $message")
                }
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        ExceptionHandler(context)
        Authenticator(datastore)
    }
}


