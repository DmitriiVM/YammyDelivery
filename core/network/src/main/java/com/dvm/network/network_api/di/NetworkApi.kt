package com.dvm.network.network_api.di

import com.dvm.module_injector.BaseAPI
import com.dvm.network.network_api.services.*

interface NetworkApi: BaseAPI {
    fun authService(): AuthService
    fun cartService(): CartService
    fun menuService(): MenuService
    fun orderService(): OrderService
    fun profileService(): ProfileService
}