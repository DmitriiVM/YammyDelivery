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
        Favorite::class,
        Hint::class,
        Recommended::class,
        Notification::class,
        Profile::class,
        Order::class,
        OrderItem::class,
        OrderStatus::class
    ],
    views = [CategoryDish::class],
    version = 7,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun dishDao(): DishDao
    abstract fun reviewDao(): ReviewDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao
    abstract fun hintDao(): HintDao
    abstract fun notificationDao(): NotificationDao
    abstract fun profileDao(): ProfileDao
    abstract fun orderDao(): OrderDao
}