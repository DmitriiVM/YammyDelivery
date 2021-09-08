package com.dvm.network.impl.di

import com.dvm.network.api.AuthApi
import com.dvm.network.api.CartApi
import com.dvm.network.api.MenuApi
import com.dvm.network.api.OrderApi
import com.dvm.network.api.ProfileApi
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.ExceptionCallAdapterFactory
import com.dvm.network.impl.TokenAuthenticator
import com.dvm.network.impl.api.DefaultAuthApi
import com.dvm.network.impl.api.DefaultCartApi
import com.dvm.network.impl.api.DefaultMenuApi
import com.dvm.network.impl.api.DefaultOrderApi
import com.dvm.network.impl.api.DefaultProfileApi
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://sandbox.skill-branch.ru/")
            .client(get())
            .addCallAdapterFactory(ExceptionCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .authenticator(get())
            .build()
    }

    single<Authenticator> {
        TokenAuthenticator(
            datastore = get(),
            authApi = lazy { get() }
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