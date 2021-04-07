package com.dvm.network.network_api.di

import com.dvm.network.network_api.services.*

interface NetworkApi {
    fun authService(): AuthService
    fun cartService(): CartService
    fun menuService(): MenuService
    fun orderService(): OrderService
    fun profileService(): ProfileService
}