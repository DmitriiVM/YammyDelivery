package com.dvm.network.network_impl

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://sandbox.skill-branch.ru/"

internal fun getRetrofit(): Retrofit {
    val httpClient=
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

