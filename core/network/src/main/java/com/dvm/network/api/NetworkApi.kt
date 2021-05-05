package com.dvm.network.api

import com.dvm.network.api.api.*

interface NetworkApi {
    fun authService(): AuthApi
    fun cartService(): CartApi
    fun menuService(): MenuApi
    fun orderService(): OrderApi
    fun profileService(): ProfileApi
}