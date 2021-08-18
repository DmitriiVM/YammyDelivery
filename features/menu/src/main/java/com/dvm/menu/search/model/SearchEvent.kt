package com.dvm.menu.search.model

internal sealed class SearchEvent {

    data class OpenCategory(
        val categoryId: String,
        val name: String
    ) : SearchEvent()

    data class OpenSubcategory(
        val categoryId: String,
        val subcategoryId: String?,
        val name: String
    ) : SearchEvent()

    data class OpenDish(val dishId: String, val name: String) : SearchEvent()
    data class RemoveHint(val hint: String) : SearchEvent()
    data class ChangeQuery(val query: String) : SearchEvent()
    data class AddToCart(val dishId: String, val name: String) : SearchEvent()
    data class SelectHint(val hint: String) : SearchEvent()
    object RemoveQuery : SearchEvent()
    object DismissAlert: SearchEvent()
    object Back : SearchEvent()
}