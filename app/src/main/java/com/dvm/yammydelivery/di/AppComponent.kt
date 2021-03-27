package com.dvm.yammydelivery.di

import android.content.Context
import com.dvm.db.di.DatabaseDependencies
import com.dvm.dish.di.DishDependencies
import com.dvm.menu.di.MenuDependencies
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