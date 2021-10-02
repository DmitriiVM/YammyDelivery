package com.dvm.network.impl

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal fun buildOkHttpClient(authenticator: TokenAuthenticator): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .authenticator(authenticator)
        .build()


internal fun buildApiService(
    context: Context,
    client: OkHttpClient
): ApiService =
    Retrofit.Builder()
        .baseUrl("https://sandbox.skill-branch.ru/")
        .client(client)
        .addCallAdapterFactory(ExceptionCallAdapterFactory(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)