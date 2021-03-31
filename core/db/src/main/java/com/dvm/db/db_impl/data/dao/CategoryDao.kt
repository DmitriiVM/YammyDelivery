package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.Category
import com.dvm.db.db_api.data.models.ParentCategory
import com.dvm.db.db_api.data.models.Subcategory

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