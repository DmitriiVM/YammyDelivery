package com.dvm.network.network_impl.di

import com.dvm.network.network_api.api.*
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.TokenAuthenticator
import com.dvm.network.network_impl.api.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
        private const val BASE_URL = "https://sandbox.skill-branch.ru/"

        @Singleton
        @Provides
        fun provideApiService(client: OkHttpClient): ApiService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        @Singleton
        @Provides
        fun provideOkHttpClient(authenticator: TokenAuthenticator): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                )
                .authenticator(authenticator)
                .build()
    }
}