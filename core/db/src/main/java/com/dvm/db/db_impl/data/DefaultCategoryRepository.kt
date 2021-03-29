package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.models.Category
import com.dvm.db.db_api.data.models.ParentCategory
import com.dvm.db.db_api.data.models.Subcategory
import com.dvm.db.db_impl.AppDatabase
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val database: AppDatabase
) : CategoryRepository{
    override suspend fun getParentCategories(): List<ParentCategory>  =
        database.categoryDao().getParentCategories()

    override suspend fun getChildCategories(id: String): List<Subcategory>  =
        database.categoryDao().getChildCategories(id)

    override suspend fun getCategoryTitle(categoryId: String): String  =
        database.categoryDao().getCategoryTitle(categoryId)

    override suspend fun insertCategories(categories: List<Category>)  =
        database.categoryDao().insertCategories(categories)
}