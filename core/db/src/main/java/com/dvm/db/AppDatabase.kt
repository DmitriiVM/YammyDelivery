package com.dvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvm.db.entities.*

@Database(
    entities = [
        Category::class,
        Dish::class,
        Review::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
    abstract fun reviewDao(): ReviewDao

    // TODO convert to object with DI
    companion object {
        @Volatile
        private var database: AppDatabase? = null

        fun getDb(context: Context) = database
            ?: synchronized(this) {
                Room
                    .databaseBuilder(context, AppDatabase::class.java, "YammyDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { database = it }
            }
    }
}