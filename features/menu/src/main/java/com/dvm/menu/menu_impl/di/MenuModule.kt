package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_impl.menu.domain.DefaultMenuInteractor
import com.dvm.menu.menu_impl.menu.domain.MenuInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface MenuModule {

    @Binds
    fun provideMenuInteractor(interactor: DefaultMenuInteractor): MenuInteractor
}