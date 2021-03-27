package com.dvm.dish.di

import com.dvm.dish.DishFragment
import dagger.Component

@Component(
    modules = [DishModule::class],
    dependencies = [DishDependencies::class]
)
abstract class DishComponent {

    abstract fun inject(dishFragment: DishFragment)
}