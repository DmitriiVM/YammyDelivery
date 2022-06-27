package com.dvm.network

import com.dvm.network.client.CIOConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val networkModule = module {

    single {
        HttpClient(
            engineFactory = CIO,
            block = CIOConfiguration(
                context = get(),
                datastore = get()
            )
        )
    }
}