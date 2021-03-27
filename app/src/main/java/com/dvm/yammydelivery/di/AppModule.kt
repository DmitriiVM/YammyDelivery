package com.dvm.yammydelivery.di

import android.content.Context
import com.dvm.db.dao.*
import com.dvm.db.di.DatabaseComponentHolder
import com.dvm.db.di.DatabaseDependencies
import com.dvm.dish.di.DishDependencies
import com.dvm.menu.di.MenuDependencies
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    fun provideDatabaseDependencies(context: Context) = object : DatabaseDependencies{
        override fun context(): Context  = context
    }

    @Provides
    fun provideMenuDependencies(): MenuDependencies = object : MenuDependencies {

        override fun categoryDao(): CategoryDao = DatabaseComponentHolder.get().categoryDao()

        override fun dishDao(): DishDao = DatabaseComponentHolder.get().dishDao()

        override fun favoriteDao(): FavoriteDao = DatabaseComponentHolder.get().favoriteDao()

        override fun cartDao(): CartDao = DatabaseComponentHolder.get().cartDao()

        override fun reviewDao(): ReviewDao = DatabaseComponentHolder.get().reviewDao()
    }

    @Provides
    fun provideDishDependencies(): DishDependencies = object : DishDependencies {

        override fun categoryDao(): CategoryDao = DatabaseComponentHolder.get().categoryDao()

        override fun dishDao(): DishDao = DatabaseComponentHolder.get().dishDao()

        override fun favoriteDao(): FavoriteDao = DatabaseComponentHolder.get().favoriteDao()

        override fun cartDao(): CartDao = DatabaseComponentHolder.get().cartDao()

        override fun reviewDao(): ReviewDao = DatabaseComponentHolder.get().reviewDao()
    }
}