package com.dvm.db.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.api.models.Category
import com.dvm.db.api.models.ParentCategory
import com.dvm.db.api.models.Subcategory
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CategoryDao {

    @Query(
        """
            SELECT *
            FROM category
            WHERE parent IS NULL
            AND active = 1
            ORDER BY `order`
        """
    )
    suspend fun getParentCategories(): List<ParentCategory>

    @Query(
        """
            SELECT *
            FROM category
            WHERE parent IS :id
            AND active = 1
            ORDER BY `order`
        """
    )
    suspend fun getSubcategories(id: String): List<Subcategory>

    @Query(
        """
            SELECT name
            FROM category
            WHERE id = :categoryId
        """
    )
    suspend fun getTitle(categoryId: String): String

    @Query(
        """
            SELECT *
            FROM category
            WHERE parent IS NULL
            AND category.name LIKE '%' || :query || '%'
            AND category.active = 1
            ORDER BY name
        """
    )
    fun searchParentCategory(query: String): Flow<List<ParentCategory>>

    @Query(
        """
            SELECT *
            FROM category
            WHERE parent IS NOT NULL
            AND category.name LIKE '%' || :query || '%'
            AND category.active = 1
            ORDER BY name
        """
    )
    fun searchSubcategory(query: String): Flow<List<Subcategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
}