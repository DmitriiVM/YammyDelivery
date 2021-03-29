package com.dvm.yammydelivery.di

import android.content.Context
import com.dvm.db.db_api.data.*
import com.dvm.db.db_impl.di.DatabaseComponentHolder
import com.dvm.db.db_impl.di.DatabaseDependencies
import com.dvm.dish.dish_impl.di.DishDependencies
import com.dvm.menu.menu_impl.di.MenuDependencies
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

        override fun categoryDao(): CategoryRepository = DatabaseComponentHolder.getApi().categoryRepository()

        override fun dishDao(): DishRepository = DatabaseComponentHolder.getApi().dishRepository()

        override fun favoriteDao(): FavoriteRepository = DatabaseComponentHolder.getApi().favoriteRepository()

        override fun cartDao(): CartRepository = DatabaseComponentHolder.getApi().cartRepository()

        override fun reviewDao(): ReviewRepository = DatabaseComponentHolder.getApi().reviewRepository()
    }

    @Provides
    fun provideDishDependencies(): DishDependencies = object : DishDependencies {

        override fun categoryDao(): CategoryRepository = DatabaseComponentHolder.getApi().categoryRepository()

        override fun dishDao(): DishRepository = DatabaseComponentHolder.getApi().dishRepository()

        override fun favoriteDao(): FavoriteRepository = DatabaseComponentHolder.getApi().favoriteRepository()

        override fun cartDao(): CartRepository = DatabaseComponentHolder.getApi().cartRepository()

        override fun reviewDao(): ReviewRepository = DatabaseComponentHolder.getApi().reviewRepository()
    }
}