package com.dvm.db.db_impl.di

import android.content.Context
import androidx.room.Room
import com.dvm.db.db_api.data.*
import com.dvm.db.db_impl.AppDatabase
import com.dvm.db.db_impl.data.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface DatabaseModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(context: Context): AppDatabase =
            Room
                .databaseBuilder(context, AppDatabase::class.java, "YammyDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }

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
}