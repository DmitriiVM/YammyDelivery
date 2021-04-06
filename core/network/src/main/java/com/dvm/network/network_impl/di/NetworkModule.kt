package com.dvm.network.network_impl.di

import com.dvm.network.network_api.services.*
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.getRetrofit
import com.dvm.network.network_impl.sevices.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    fun provideAuthService(authService : DefaultAuthService): AuthService

    @Binds
    fun provideCartService(cartService : DefaultCartService): CartService

    @Binds
    fun provideMenuService(menuService : DefaultMenuService): MenuService

    @Binds
    fun provideOrderService(orderService : DefaultOrderService): OrderService

    @Binds
    fun provideProfileService(profileService : DefaultProfileService): ProfileService

    companion object{

        @Singleton
        @Provides
        fun provideApiService(): ApiService = getRetrofit().create(ApiService::class.java)
    }
}