package com.dvm.yammydelivery.di

import android.content.Context
import com.dvm.auth.auth_impl.di.AuthComponentHolder
import com.dvm.auth.auth_impl.di.AuthDependencies
import com.dvm.db.db_api.data.*
import com.dvm.db.db_impl.di.DatabaseComponentHolder
import com.dvm.db.db_impl.di.DatabaseDependencies
import com.dvm.dish.dish_impl.di.DishComponentHolder
import com.dvm.dish.dish_impl.di.DishDependencies
import com.dvm.menu.menu_impl.di.MenuComponentHolder
import com.dvm.menu.menu_impl.di.MenuDependencies
import com.dvm.preferences.datastore_impl.di.DatastoreComponentHolder
import com.dvm.preferences.datastore_impl.di.DatastoreDependencies

internal fun provideDependencies(context: Context) {

    DatabaseComponentHolder.dependencies = {
        object : DatabaseDependencies {
            override fun context(): Context = context
        }
    }

    DatastoreComponentHolder.dependencies = {
        object : DatastoreDependencies {
            override fun context(): Context = context
        }
    }

    DishComponentHolder.dependencies = {
        object : DishDependencies {

            override fun categoryDao(): CategoryRepository =
                DatabaseComponentHolder.getApi().categoryRepository()

            override fun dishDao(): DishRepository =
                DatabaseComponentHolder.getApi().dishRepository()

            override fun favoriteDao(): FavoriteRepository =
                DatabaseComponentHolder.getApi().favoriteRepository()

            override fun cartDao(): CartRepository =
                DatabaseComponentHolder.getApi().cartRepository()

            override fun reviewDao(): ReviewRepository =
                DatabaseComponentHolder.getApi().reviewRepository()
        }
    }

    MenuComponentHolder.dependencies = {
        object : MenuDependencies {

            override fun categoryDao(): CategoryRepository =
                DatabaseComponentHolder.getApi().categoryRepository()

            override fun dishDao(): DishRepository =
                DatabaseComponentHolder.getApi().dishRepository()

            override fun favoriteDao(): FavoriteRepository =
                DatabaseComponentHolder.getApi().favoriteRepository()

            override fun cartDao(): CartRepository =
                DatabaseComponentHolder.getApi().cartRepository()

            override fun reviewDao(): ReviewRepository =
                DatabaseComponentHolder.getApi().reviewRepository()
        }
    }

    AuthComponentHolder.dependencies = {
        object : AuthDependencies {
            override fun temp() = "temp"

        }
    }
}