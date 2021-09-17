package com.dvm.database.impl.repositories

import com.dvm.database.Category
import com.dvm.database.CategoryQueries
import com.dvm.database.ParentCategory
import com.dvm.database.Subcategory
import com.dvm.database.api.CategoryRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultCategoryRepository(
    private val categoryQueries: CategoryQueries
) : CategoryRepository {

    override fun searchParentCategory(query: String): Flow<List<ParentCategory>> =
        categoryQueries.searchParentCategory(
            query = query,
            mapper = { id, name, icon ->
                ParentCategory(
                    id = id,
                    name = name,
                    icon = icon
                )
            }
        )
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun searchSubcategory(query: String): Flow<List<Subcategory>> =
        categoryQueries
            .searchSubcategory(
                query = query,
                mapper = { id, name, parent ->
                    Subcategory(
                        id = id,
                        name = name,
                        parent = parent
                    )
                }
            )
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun getParentCategories(): List<ParentCategory> =
        withContext(Dispatchers.IO) {
            categoryQueries
                .parentCategory()
                .executeAsList()
        }

    override suspend fun getSubcategories(parentId: String): List<Subcategory> =
        withContext(Dispatchers.IO) {
            categoryQueries
                .subcategory(parentId)
                .executeAsList()
        }

    override suspend fun getCategoryTitle(categoryId: String): String =
        withContext(Dispatchers.IO) {
            categoryQueries
                .title(categoryId)
                .executeAsOne()
        }

    override suspend fun insertCategories(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            categoryQueries.transaction {
                categories.forEach {
                    categoryQueries.insert(it)
                }
            }
        }
}