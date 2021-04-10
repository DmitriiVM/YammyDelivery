package com.dvm.menu.search.model

internal sealed class SearchEvent {
    data class QueryChange(val query: String) : SearchEvent()
    data class HintClick(val hint: String) : SearchEvent()
    data class CategoryClick(val categoryId: String) : SearchEvent()
    data class SubcategoryClick(val subcategoryId: String) : SearchEvent()
    data class DishClick(val dishId: String) : SearchEvent()
    data class RemoveHintClick(val hint: String) : SearchEvent()
    data class AddToCart(val dishId: String) : SearchEvent()
    data class AddToFavorite(val dishId: String) : SearchEvent()
    object DismissAlert : SearchEvent()
    object BackClick : SearchEvent()
}