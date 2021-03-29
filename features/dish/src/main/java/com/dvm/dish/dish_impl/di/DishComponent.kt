package com.dvm.dish.dish_impl.di

import com.dvm.dish.dish_api.DishApi
import com.dvm.dish.dish_impl.presentation.DishFragment
import com.dvm.utils.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [DishModule::class],
    dependencies = [DishDependencies::class]
)
internal interface DishComponent: DishApi {

    fun inject(dishFragment: DishFragment)

    companion object {
        fun initAndGet(menuDependencies: DishDependencies): DishComponent {
            return DaggerDishComponent.builder()
                .dishDependencies(menuDependencies)
                .build()
        }
    }
}