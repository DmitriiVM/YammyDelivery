package com.dvm.network

object ApiService {
    fun getNetworkService() = getRetrofit().create(Api::class.java)
}