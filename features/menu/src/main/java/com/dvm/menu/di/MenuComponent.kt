package com.dvm.menu.di

import com.dvm.menu.category.presentation.CategoryFragment
import com.dvm.menu.menu.presentation.MenuFragment
import dagger.Component

@Component(
    modules = [MenuModule::class],
    dependencies = [MenuDependencies::class]
)
interface MenuComponent {

    fun inject(menuFragment: MenuFragment)
    fun inject(categoryFragment: CategoryFragment)
}