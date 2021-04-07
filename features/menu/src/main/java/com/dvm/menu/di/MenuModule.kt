package com.dvm.menu.di

import com.dvm.menu.menu.domain.DefaultMenuInteractor
import com.dvm.menu.menu.domain.MenuInteractor
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