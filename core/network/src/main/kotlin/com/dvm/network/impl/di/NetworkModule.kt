package com.dvm.network.impl.di

import com.dvm.network.api.*
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api.*
import com.dvm.network.impl.client.CIOConfiguration
import io.ktor.client.*
import io.ktor.client.engine.cio.*
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

    single {
        ApiService(
            client = get()
        )
    }

    factory<AuthApi> {
        DefaultAuthApi(
            apiService = get()
        )
    }

    factory<CartApi> {
        DefaultCartApi(
            apiService = get()
        )
    }

    factory<MenuApi> {
        DefaultMenuApi(
            apiService = get()
        )
    }

    factory<OrderApi> {
        DefaultOrderApi(
            apiService = get()
        )
    }

    factory<ProfileApi> {
        DefaultProfileApi(
            apiService = get()
        )
    }
}