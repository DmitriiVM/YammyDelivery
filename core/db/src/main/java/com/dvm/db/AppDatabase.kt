package com.dvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvm.db.dao.*
import com.dvm.db.entities.Category
import com.dvm.db.entities.Dish
import com.dvm.db.entities.Review

@Database(
    entities = [
        Category::class,
        Dish::class,
        Review::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
    abstract fun reviewDao(): ReviewDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao
}