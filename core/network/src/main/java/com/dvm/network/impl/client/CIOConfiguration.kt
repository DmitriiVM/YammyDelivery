package com.dvm.network.impl.client

import android.content.Context
import android.util.Log
import com.dvm.network.Authenticator
import com.dvm.preferences.api.DatastoreRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

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
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        ExceptionHandler(context)
        Authenticator(datastore)
    }
}


