package com.dvm.dish.dish_impl.di

import com.dvm.dish.dish_api.DishApi
import com.dvm.module_injector.ComponentHolder

object DishComponentHolder: ComponentHolder<DishApi, DishDependencies> {

    private var dishComponent: DishComponent? = null

    override lateinit var dependencies: () -> DishDependencies

    override fun init() {
        if (dishComponent == null){
            synchronized(this){
                if (dishComponent == null){
                    dishComponent = DishComponent.initAndGet(dependencies())
                }
            }
        }
    }

    override fun getApi(): DishApi = getComponent()

    internal fun getComponent(): DishComponent {
        checkNotNull(dishComponent){ "DishComponent is not initialized!"}
        return dishComponent!!
    }
}