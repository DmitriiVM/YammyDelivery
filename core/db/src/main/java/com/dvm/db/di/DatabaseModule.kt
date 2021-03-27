package com.dvm.db.di

import android.content.Context
import androidx.room.Room
import com.dvm.db.AppDatabase
import com.dvm.db.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, "YammyDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()

    @Provides
    fun provideDishDao(appDatabase: AppDatabase): DishDao = appDatabase.dishDao()

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao = appDatabase.cartDao()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.favoriteDao()

    @Provides
    fun provideReviewDao(appDatabase: AppDatabase): ReviewDao = appDatabase.reviewDao()
}