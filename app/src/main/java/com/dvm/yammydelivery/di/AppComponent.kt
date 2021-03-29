package com.dvm.yammydelivery.di

import android.content.Context
import com.dvm.db.db_impl.di.DatabaseDependencies
import com.dvm.dish.dish_impl.di.DishDependencies
import com.dvm.menu.menu_impl.di.MenuDependencies
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun databaseDependencies() : DatabaseDependencies
    fun menuDependencies(): MenuDependencies
    fun dishDependencies(): DishDependencies
}