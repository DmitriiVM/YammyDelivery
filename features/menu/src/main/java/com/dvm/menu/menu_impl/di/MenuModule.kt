package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuLauncher
import com.dvm.menu.menu_impl.launcher.DefaultMenuLauncher
import com.dvm.menu.menu_impl.menu.domain.DefaultMenuInteractor
import com.dvm.menu.menu_impl.menu.domain.MenuInteractor
import com.dvm.utils.di.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface MenuModule {

    @FeatureScope
    @Binds
    fun provideMenuInteractor(interactor: DefaultMenuInteractor): MenuInteractor

    @FeatureScope
    @Binds
    fun provideMenuLauncher(launcher: DefaultMenuLauncher): MenuLauncher
}