package com.dvm.db.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvm.db.api.models.CardDishDetails
import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.Category
import com.dvm.db.api.models.Dish
import com.dvm.db.api.models.Favorite
import com.dvm.db.api.models.Hint
import com.dvm.db.api.models.Notification
import com.dvm.db.api.models.Order
import com.dvm.db.api.models.OrderItem
import com.dvm.db.api.models.OrderStatus
import com.dvm.db.api.models.Profile
import com.dvm.db.api.models.Recommended
import com.dvm.db.api.models.Review
import com.dvm.db.impl.dao.CartDao
import com.dvm.db.impl.dao.CategoryDao
import com.dvm.db.impl.dao.DishDao
import com.dvm.db.impl.dao.FavoriteDao
import com.dvm.db.impl.dao.HintDao
import com.dvm.db.impl.dao.NotificationDao
import com.dvm.db.impl.dao.OrderDao
import com.dvm.db.impl.dao.ProfileDao
import com.dvm.db.impl.dao.ReviewDao
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
