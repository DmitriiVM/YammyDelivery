package com.dvm.db.impl.di

import android.content.Context
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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Binds
    fun provideCategoryRepository(repository: DefaultCategoryRepository): CategoryRepository

    @Binds
    fun provideDishRepository(repository: DefaultDishRepository): DishRepository

    @Binds
    fun provideCartRepository(repository: DefaultCartRepository): CartRepository

    @Binds
    fun provideFavoriteRepository(repository: DefaultFavoriteRepository): FavoriteRepository

    @Binds
    fun provideReviewRepository(repository: DefaultReviewRepository): ReviewRepository

    @Binds
    fun provideNotificationRepository(repository: DefaultNotificationRepository): NotificationRepository

    @Binds
    fun provideProfileRepository(repository: DefaultProfileRepository): ProfileRepository

    @Binds
    fun provideOrderRepository(repository: DefaultOrderRepository): OrderRepository

    @Binds
    fun provideHintRepository(repository: DefaultHintRepository): HintRepository

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room
                .databaseBuilder(context, AppDatabase::class.java, "YammyDatabase")
                .fallbackToDestructiveMigration()
                .build()

        @Provides
        fun provideCartDao(database: AppDatabase) = database.cartDao()

        @Provides
        fun provideDishDao(database: AppDatabase) = database.dishDao()

        @Provides
        fun provideCategoryDao(database: AppDatabase) = database.categoryDao()

        @Provides
        fun provideFavoriteDao(database: AppDatabase) = database.favoriteDao()

        @Provides
        fun provideReviewDao(database: AppDatabase) = database.reviewDao()

        @Provides
        fun provideHintDao(database: AppDatabase) = database.hintDao()

        @Provides
        fun provideNotificationDao(database: AppDatabase) = database.notificationDao()

        @Provides
        fun provideProfileDao(database: AppDatabase) = database.profileDao()

        @Provides
        fun provideOrderDao(database: AppDatabase) = database.orderDao()
    }
}