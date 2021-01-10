package com.dvm.yammydelivery.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeliveryApiService {

    val baseUrl = "https://polls.apiblueprint.org/"

    private fun getRetrofit()= Retrofit.Builder()
        .baseUrl(baseUrl)
//        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun api() = getRetrofit().create(DeliveryApi::class.java)
}