package com.dvm.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.entities.Category
import com.dvm.db.entities.ParentCategory
import com.dvm.db.entities.Subcategory

@Dao
interface CategoryDao {

    @Query(
        """
        SELECT *
        FROM category
        WHERE parent IS NULL
        AND active = 1
        ORDER BY `order`
    """
    )
    suspend fun getParentCategories(): List<ParentCategory> // TODO flow

    @Query(
        """
        SELECT *
        FROM category
        WHERE parent IS :id
        AND active = 1
        ORDER BY `order`
    """
    )
    suspend fun getChildCategories(id: String): List<Subcategory> // TODO flow

    @Query(
        """
            SELECT name
            FROM category
            WHERE id = :categoryId
        """
    )
    suspend fun getCategoryTitle(categoryId: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
}