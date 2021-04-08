package com.dvm.network.network_api.di

import com.dvm.network.network_api.api.*

interface NetworkApi {
    fun authService(): AuthApi
    fun cartService(): CartApi
    fun menuService(): MenuApi
    fun orderService(): OrderApi
    fun profileService(): ProfileApi
}