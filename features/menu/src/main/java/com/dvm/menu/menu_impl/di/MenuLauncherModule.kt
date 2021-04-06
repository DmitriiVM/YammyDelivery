package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuLauncher
import com.dvm.menu.menu_impl.launcher.DefaultMenuLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MenuLauncherModule {

    @Singleton
    @Binds
    fun provideMenuLauncher(launcher: DefaultMenuLauncher): MenuLauncher
}