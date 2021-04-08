package com.dvm.db.db_impl.di

import android.content.Context
import androidx.room.Room
import com.dvm.db.db_api.data.*
import com.dvm.db.db_impl.AppDatabase
import com.dvm.db.db_impl.data.*
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
    }
}