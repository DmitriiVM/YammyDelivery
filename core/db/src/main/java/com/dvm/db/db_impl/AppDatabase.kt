package com.dvm.db.db_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvm.db.db_api.data.models.*
import com.dvm.db.db_impl.data.dao.*

@Database(
    entities = [
        Category::class,
        Dish::class,
        Review::class,
        Cart::class,
        Favorite::class
    ],
    version = 5,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
    abstract fun reviewDao(): ReviewDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao
}