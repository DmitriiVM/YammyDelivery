package com.dvm.dish.dish_impl.di

import com.dvm.dish.dish_api.DishLauncher
import com.dvm.dish.dish_impl.launcher.DefaultDishLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DishLauncherModule {

    @Singleton
    @Binds
    fun provideDishLauncher(launcher: DefaultDishLauncher): DishLauncher
}