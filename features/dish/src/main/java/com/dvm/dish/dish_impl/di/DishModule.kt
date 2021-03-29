package com.dvm.dish.dish_impl.di

import com.dvm.dish.dish_api.DishLauncher
import com.dvm.dish.dish_impl.launcher.DefaultDishLauncher
import dagger.Binds
import dagger.Module

@Module
interface DishModule{
    @Binds
    fun provideDishLauncher(launcher: DefaultDishLauncher): DishLauncher
}