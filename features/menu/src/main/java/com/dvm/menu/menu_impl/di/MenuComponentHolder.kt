package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuApi
import com.dvm.module_injector.ComponentHolder

object MenuComponentHolder: ComponentHolder<MenuApi, MenuDependencies> {

    private var menuComponent: MenuComponent? = null

    override lateinit var dependencies: () -> MenuDependencies

    override fun init() {
        if (menuComponent == null){
            synchronized(this){
                if (menuComponent == null){
                    menuComponent = MenuComponent.initAndGet(dependencies())
                }
            }
        }
    }

    override fun getApi(): MenuApi  = getComponent()

    override fun destroy() {
        menuComponent = null
    }

    internal fun getComponent(): MenuComponent {
        checkNotNull(menuComponent){ "MenuComponent is not initialized!"}
        return menuComponent!!
    }
}