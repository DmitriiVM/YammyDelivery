package com.dvm.db.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvm.db.api.models.*
import com.dvm.db.impl.dao.*
import com.dvm.db.impl.data.DateConverter

@Database(
    entities = [
        Category::class,
        Dish::class,
        Review::class,
        CartItem::class,
        Favorite::class,
        Hint::class,
        Recommended::class,
        Notification::class,
        Profile::class,
        Order::class,
        OrderItem::class,
        OrderStatus::class
    ],
    views = [CardDishDetails::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
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