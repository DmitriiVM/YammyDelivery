package com.dvm.yammydelivery.di

import com.dvm.utils.AppLauncher
import com.dvm.yammydelivery.DefaultAppLauncher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun provideAppLauncher(appLauncher: DefaultAppLauncher): AppLauncher

    companion object {
        @Provides
        fun provideServiceScope(): CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}