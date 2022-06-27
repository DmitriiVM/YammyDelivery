package com.dvm.menu_impl.data.database

import com.dvm.menu_api.domain.model.Category
import com.dvm.menu_api.domain.model.ParentCategory
import com.dvm.menu_api.domain.model.Subcategory
import com.dvm.menu_impl.data.mappers.toCategoryEntity
import com.dvm.menu_impl.data.mappers.toParentCategory
import com.dvm.menu_impl.data.mappers.toSubcategory
import com.dvm.menu_impl.domain.repository.CategoryRepository
import com.dvm.menudatabase.CategoryQueries
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
                .parentCategoryEntity()
                .executeAsList()
                .map { it.toParentCategory() }
        }

    override suspend fun getSubcategories(parentId: String): List<Subcategory> =
        withContext(Dispatchers.IO) {
            categoryQueries
                .subcategoryEntity(parentId)
                .executeAsList()
                .map { it.toSubcategory() }
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
                    categoryQueries.insert(it.toCategoryEntity())
                }
            }
        }
}