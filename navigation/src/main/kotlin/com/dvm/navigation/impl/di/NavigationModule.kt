package com.dvm.navigation.impl.di

import com.dvm.navigation.api.Navigator
import com.dvm.navigation.impl.DefaultNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {

    @Singleton
    @Binds
    fun provideNavigator(navigator: DefaultNavigator): Navigator
}