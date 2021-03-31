package com.dvm.network.network_impl

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://sandbox.skill-branch.ru/"

internal fun getRetrofit() = Retrofit.Builder()
    .baseUrl(BASE_URL)
//        .client(getOkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

