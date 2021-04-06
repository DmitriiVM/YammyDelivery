package com.dvm.auth.auth_impl.di

import com.dvm.auth.auth_api.AuthLauncher
import com.dvm.auth.auth_impl.launcher.DefaultAuthLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthLauncherModule{

    @Singleton
    @Binds
    fun provideAuthLauncher(launcher: DefaultAuthLauncher): AuthLauncher
}