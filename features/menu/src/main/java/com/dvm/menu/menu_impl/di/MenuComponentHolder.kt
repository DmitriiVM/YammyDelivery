package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuApi
import com.dvm.module_injector.ComponentHolder

object MenuComponentHolder: ComponentHolder<MenuApi, MenuDependencies> {

    private var menuComponent: MenuComponent? = null

    override fun init(dependencies: MenuDependencies) {
        if (menuComponent == null){
            synchronized(this){
                if (menuComponent == null){
                    menuComponent = MenuComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun getApi(): MenuApi  = getComponent()

    internal fun getComponent(): MenuComponent {
        checkNotNull(menuComponent){ "MenuComponent is not initialized!"}
        return menuComponent!!
    }
}