package com.dvm.db.di

import com.dvm.db.dao.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DatabaseModule::class],
    dependencies = [DatabaseDependencies::class]
)
interface DatabaseComponent {

    fun categoryDao(): CategoryDao
    fun dishDao(): DishDao
    fun reviewDao(): ReviewDao
    fun favoriteDao(): FavoriteDao
    fun cartDao(): CartDao
}