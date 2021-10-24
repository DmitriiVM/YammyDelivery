package com.dvm.database.impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.CartItem
import com.dvm.database.api.models.Category
import com.dvm.database.api.models.Dish
import com.dvm.database.api.models.Favorite
import com.dvm.database.api.models.Hint
import com.dvm.database.api.models.Notification
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderItem
import com.dvm.database.api.models.OrderStatus
import com.dvm.database.api.models.Profile
import com.dvm.database.api.models.Recommended
import com.dvm.database.api.models.Review
import com.dvm.database.impl.dao.CartDao
import com.dvm.database.impl.dao.CategoryDao
import com.dvm.database.impl.dao.DishDao
import com.dvm.database.impl.dao.FavoriteDao
import com.dvm.database.impl.dao.HintDao
import com.dvm.database.impl.dao.NotificationDao
import com.dvm.database.impl.dao.OrderDao
import com.dvm.database.impl.dao.ProfileDao
import com.dvm.database.impl.dao.ReviewDao

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

internal fun buildDatabase(context: Context) =
    Room
        .databaseBuilder(context, AppDatabase::class.java, "YammyDatabase")
        .fallbackToDestructiveMigration()
        .build()