package com.dvm.network.impl.di

import android.content.Context
import com.dvm.network.api.AuthApi
import com.dvm.network.api.CartApi
import com.dvm.network.api.MenuApi
import com.dvm.network.api.OrderApi
import com.dvm.network.api.ProfileApi
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.TokenAuthenticator
import com.dvm.network.impl.api.DefaultAuthApi
import com.dvm.network.impl.api.DefaultCartApi
import com.dvm.network.impl.api.DefaultMenuApi
import com.dvm.network.impl.api.DefaultOrderApi
import com.dvm.network.impl.api.DefaultProfileApi
import com.dvm.network.impl.buildApiService
import com.dvm.network.impl.buildOkHttpClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    fun provideAuthService(authService: DefaultAuthApi): AuthApi

    @Binds
    fun provideCartService(cartService: DefaultCartApi): CartApi

    @Binds
    fun provideMenuService(menuService: DefaultMenuApi): MenuApi

    @Binds
    fun provideOrderService(orderService: DefaultOrderApi): OrderApi

    @Binds
    fun provideProfileService(profileService: DefaultProfileApi): ProfileApi

    companion object {

        @Singleton
        @Provides
        fun provideApiService(
            @ApplicationContext context: Context,
            client: OkHttpClient
        ): ApiService =
            buildApiService(
                context = context,
                client = client
            )

        @Singleton
        @Provides
        fun provideOkHttpClient(
            authenticator: TokenAuthenticator
        ): OkHttpClient =
            buildOkHttpClient(authenticator)
    }
}