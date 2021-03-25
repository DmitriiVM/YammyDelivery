package com.dvm.menu.category.presentation.model

sealed class CategoryEvent {
    data class AddToCart(val dishId: String): CategoryEvent()
    data class AddToFavorite(val dishId: String): CategoryEvent()
    data class NavigateToSubcategory(val id: String): CategoryEvent()
    data class NavigateToDish(val dishId: String): CategoryEvent()
    data class Sort(val sortType: SortType): CategoryEvent()
    object NavigateUp: CategoryEvent()
}