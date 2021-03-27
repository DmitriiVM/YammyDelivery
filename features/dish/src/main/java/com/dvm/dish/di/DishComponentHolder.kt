package com.dvm.dish.di

object DishComponentHolder {

    private var dishComponent: DishComponent? = null

    fun init(dishDependencies: DishDependencies) {
        if (dishComponent == null){
            synchronized(this){
                if (dishComponent == null){
                    dishComponent = DaggerDishComponent.builder().dishDependencies(dishDependencies).build()
                }
            }
        }
    }

    fun getComponent(): DishComponent {
        checkNotNull(dishComponent){ "DishComponent is not initialized!     temp"}
        return dishComponent!!
    }
}