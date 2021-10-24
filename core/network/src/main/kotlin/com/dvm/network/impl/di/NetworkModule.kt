package com.dvm.network.impl.di

import com.dvm.network.api.AuthApi
import com.dvm.network.api.CartApi
import com.dvm.network.api.MenuApi
import com.dvm.network.api.OrderApi
import com.dvm.network.api.ProfileApi
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.api.DefaultAuthApi
import com.dvm.network.impl.api.DefaultCartApi
import com.dvm.network.impl.api.DefaultMenuApi
import com.dvm.network.impl.api.DefaultOrderApi
import com.dvm.network.impl.api.DefaultProfileApi
import com.dvm.network.impl.client.CIOConfiguration
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