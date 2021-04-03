package com.dvm.auth.auth_impl.di

import com.dvm.auth.auth_api.AuthLauncher
import com.dvm.auth.auth_impl.launcher.DefaultAuthLauncher
import com.dvm.utils.di.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface AuthModule{

    @FeatureScope
    @Binds
    fun provideAuthLauncher(launcher: DefaultAuthLauncher): AuthLauncher
}