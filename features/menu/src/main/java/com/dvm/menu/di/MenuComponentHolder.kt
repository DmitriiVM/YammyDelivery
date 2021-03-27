package com.dvm.menu.di

object MenuComponentHolder {

    private var menuComponent: MenuComponent? = null

    fun init(menuDependencies: MenuDependencies) {
        if (menuComponent == null){
            synchronized(this){
                if (menuComponent == null){
                    menuComponent = DaggerMenuComponent.builder().menuDependencies(menuDependencies).build()
                }
            }
        }
    }

    fun getComponent(): MenuComponent {
        checkNotNull(menuComponent){ "MenuComponent is not initialized!     temp"}
        return menuComponent!!
    }
}