package com.dvm.menu.search.model

internal sealed class SearchEvent {

    data class CategoryClick(
        val categoryId: String,
        val name: String
    ) : SearchEvent()

    data class SubcategoryClick(
        val categoryId: String,
        val subcategoryId: String?,
        val name: String
    ) : SearchEvent()

    data class DishClick(val dishId: String, val name: String) : SearchEvent()
    data class RemoveHintClick(val hint: String) : SearchEvent()
    data class QueryChange(val query: String) : SearchEvent()
    data class AddToCart(val dishId: String) : SearchEvent()
    data class HintClick(val hint: String) : SearchEvent()
    object RemoveQueryClick : SearchEvent()
    object BackClick : SearchEvent()
}