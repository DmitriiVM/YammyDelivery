package com.dvm.database.impl.di

import android.content.Context
import com.dvm.database.api.*
import com.dvm.database.impl.AppDatabase
import com.dvm.database.impl.buildDatabase
import com.dvm.database.impl.repositories.*
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
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase =
            buildDatabase(context)

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