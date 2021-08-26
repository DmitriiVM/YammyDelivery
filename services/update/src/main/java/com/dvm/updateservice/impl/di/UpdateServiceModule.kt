package com.dvm.updateservice.impl.di

import com.dvm.updateservice.api.UpdateService
import com.dvm.updateservice.impl.DefaultUpdateService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UpdateServiceModule {

    @Binds
    fun provideUpdateService(orderService: DefaultUpdateService): UpdateService
}