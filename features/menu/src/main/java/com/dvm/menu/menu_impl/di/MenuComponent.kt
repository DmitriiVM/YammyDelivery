package com.dvm.menu.menu_impl.di

import com.dvm.menu.menu_api.MenuApi
import com.dvm.menu.menu_impl.category.presentation.CategoryFragment
import com.dvm.menu.menu_impl.menu.presentation.MenuFragment
import dagger.Component

@Component(
    modules = [MenuModule::class],
    dependencies = [MenuDependencies::class]
)
internal interface MenuComponent: MenuApi {

    fun inject(menuFragment: MenuFragment)
    fun inject(categoryFragment: CategoryFragment)

    companion object {
        fun initAndGet(menuDependencies: MenuDependencies): MenuComponent {
            return DaggerMenuComponent.builder()
                .menuDependencies(menuDependencies)
                .build()
        }
    }
}