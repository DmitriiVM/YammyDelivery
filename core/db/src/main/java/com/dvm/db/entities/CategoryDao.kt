package com.dvm.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)




}