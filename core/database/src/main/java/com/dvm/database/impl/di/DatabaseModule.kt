package com.dvm.database.impl.di

import com.dvm.database.AppDatabase
import com.dvm.database.Hint
import com.dvm.database.OrderDetails
import com.dvm.database.api.CartRepository
import com.dvm.database.api.CategoryRepository
import com.dvm.database.api.DishRepository
import com.dvm.database.api.FavoriteRepository
import com.dvm.database.api.HintRepository
import com.dvm.database.api.NotificationRepository
import com.dvm.database.api.OrderRepository
import com.dvm.database.api.ProfileRepository
import com.dvm.database.api.ReviewRepository
import com.dvm.database.impl.DateAdapter
import com.dvm.database.impl.repositories.DefaultCartRepository
import com.dvm.database.impl.repositories.DefaultCategoryRepository
import com.dvm.database.impl.repositories.DefaultDishRepository
import com.dvm.database.impl.repositories.DefaultFavoriteRepository
import com.dvm.database.impl.repositories.DefaultHintRepository
import com.dvm.database.impl.repositories.DefaultNotificationRepository
import com.dvm.database.impl.repositories.DefaultOrderRepository
import com.dvm.database.impl.repositories.DefaultProfileRepository
import com.dvm.database.impl.repositories.DefaultReviewRepository
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

val databaseModule = module {

    single {
        val driver: SqlDriver = AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = get(),
            name = "AppDatabase"
        )
        AppDatabase(
            driver = driver,
            hintAdapter = Hint.Adapter(DateAdapter),
            orderDetailsAdapter = OrderDetails.Adapter(DateAdapter),
        )
    }

    single {
        get<AppDatabase>().cartQueries
    }

    single {
        get<AppDatabase>().categoryQueries
    }

    single {
        get<AppDatabase>().dishQueries
    }

    single {
        get<AppDatabase>().favoriteQueries
    }

    single {
        get<AppDatabase>().hintQueries
    }

    single {
        get<AppDatabase>().notificationQueries
    }

    single {
        get<AppDatabase>().orderDetailsQueries
    }

    single {
        get<AppDatabase>().orderItemQueries
    }

    single {
        get<AppDatabase>().orderStatusQueries
    }

    single {
        get<AppDatabase>().profileQueries
    }

    single {
        get<AppDatabase>().recommendedQueries
    }

    single {
        get<AppDatabase>().reviewQueries
    }

    factory<CategoryRepository> {
        DefaultCategoryRepository(
            categoryQueries = get()
        )
    }

    factory<DishRepository> {
        DefaultDishRepository(
            dishQueries = get(),
            recommendedQueries = get(),
            reviewsQueries = get()
        )
    }

    factory<CartRepository> {
        DefaultCartRepository(
            cartQueries = get()
        )
    }

    factory<FavoriteRepository> {
        DefaultFavoriteRepository(
            favoriteQueries = get()
        )
    }

    factory<ReviewRepository> {
        DefaultReviewRepository(
            reviewsQueries = get()
        )
    }

    factory<NotificationRepository> {
        DefaultNotificationRepository(
            notificationQueries = get()
        )
    }

    factory<ProfileRepository> {
        DefaultProfileRepository(
            profileQueries = get()
        )
    }

    factory<OrderRepository> {
        DefaultOrderRepository(
            orderQueries = get(),
            orderItemQueries = get(),
            orderStatusQueries = get()
        )
    }

    factory<HintRepository> {
        DefaultHintRepository(
            hintQueries = get()
        )
    }
}