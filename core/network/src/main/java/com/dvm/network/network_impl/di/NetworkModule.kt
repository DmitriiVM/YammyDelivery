package com.dvm.network.network_impl.di

import com.dvm.network.network_api.api.*
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.api.*
import com.dvm.network.network_impl.getRetrofit
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
    fun provideAuthService(authService : DefaultAuthApi): AuthApi

    @Binds
    fun provideCartService(cartService : DefaultCartApi): CartApi

    @Binds
    fun provideMenuService(menuService : DefaultMenuApi): MenuApi

    @Binds
    fun provideOrderService(orderService : DefaultOrderApi): OrderApi

    @Binds
    fun provideProfileService(profileService : DefaultProfileApi): ProfileApi

    companion object{

        @Singleton
        @Provides
        fun provideApiService(): ApiService = getRetrofit().create(ApiService::class.java)
    }
}