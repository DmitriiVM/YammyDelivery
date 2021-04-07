package com.dvm.menu.menu_impl.category.presentation.model

internal sealed class CategoryEvent {
    data class AddToCart(val dishId: String): CategoryEvent()
    data class AddToFavorite(val dishId: String): CategoryEvent()
    data class ChangeSubcategory(val id: String): CategoryEvent()
    data class NavigateToDish(val dishId: String): CategoryEvent()
    data class Sort(val sortType: SortType): CategoryEvent()
    object NavigateUp: CategoryEvent()
}