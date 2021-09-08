package com.dvm.db.impl.di

import androidx.room.Room
import com.dvm.db.api.CartRepository
import com.dvm.db.api.CategoryRepository
import com.dvm.db.api.DishRepository
import com.dvm.db.api.FavoriteRepository
import com.dvm.db.api.HintRepository
import com.dvm.db.api.NotificationRepository
import com.dvm.db.api.OrderRepository
import com.dvm.db.api.ProfileRepository
import com.dvm.db.api.ReviewRepository
import com.dvm.db.impl.AppDatabase
import com.dvm.db.impl.repositories.DefaultCartRepository
import com.dvm.db.impl.repositories.DefaultCategoryRepository
import com.dvm.db.impl.repositories.DefaultDishRepository
import com.dvm.db.impl.repositories.DefaultFavoriteRepository
import com.dvm.db.impl.repositories.DefaultHintRepository
import com.dvm.db.impl.repositories.DefaultNotificationRepository
import com.dvm.db.impl.repositories.DefaultOrderRepository
import com.dvm.db.impl.repositories.DefaultProfileRepository
import com.dvm.db.impl.repositories.DefaultReviewRepository
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room
            .databaseBuilder(
                get(),
                AppDatabase::class.java,
                "YammyDatabase"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().cartDao()
    }

    single {
        get<AppDatabase>().dishDao()
    }

    single {
        get<AppDatabase>().categoryDao()
    }

    single {
        get<AppDatabase>().favoriteDao()
    }

    single {
        get<AppDatabase>().reviewDao()
    }

    single {
        get<AppDatabase>().hintDao()
    }

    single {
        get<AppDatabase>().notificationDao()
    }

    single {
        get<AppDatabase>().profileDao()
    }

    single {
        get<AppDatabase>().orderDao()
    }

    factory<CategoryRepository> {
        DefaultCategoryRepository(
            categoryDao = get()
        )
    }

    factory<DishRepository> {
        DefaultDishRepository(
            dishDao = get()
        )
    }

    factory<CartRepository> {
        DefaultCartRepository(
            cartDao = get()
        )
    }

    factory<FavoriteRepository> {
        DefaultFavoriteRepository(
            favoriteDao = get()
        )
    }

    factory<ReviewRepository> {
        DefaultReviewRepository(
            reviewDao = get()
        )
    }

    factory<NotificationRepository> {
        DefaultNotificationRepository(
            notificationDao = get()
        )
    }

    factory<ProfileRepository> {
        DefaultProfileRepository(
            profileDao = get()
        )
    }

    factory<OrderRepository> {
        DefaultOrderRepository(
            orderDao = get()
        )
    }

    factory<HintRepository> {
        DefaultHintRepository(
            hintDao = get()
        )
    }
}