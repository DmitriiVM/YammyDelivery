package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuLauncher
import com.dvm.menu.menu_impl.launcher.DefaultMenuLauncher
import com.dvm.menu.menu_impl.menu.domain.DefaultMenuInteractor
import com.dvm.menu.menu_impl.menu.domain.MenuInteractor
import dagger.Binds
import dagger.Module

@Module
internal interface MenuModule {

    @Binds
    fun provideMenuInteractor(interactor: DefaultMenuInteractor): MenuInteractor

    @Binds
    fun provideMenuLauncher(launcher: DefaultMenuLauncher): MenuLauncher
}