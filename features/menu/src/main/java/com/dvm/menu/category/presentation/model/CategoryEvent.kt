package com.dvm.menu.category.presentation.model

sealed class CategoryEvent {
    data class SubcategoryClick(val id: String): CategoryEvent()
    data class AddToCartClick(val dishId: String): CategoryEvent()
    data class AddToFavoriteClick(val dishId: String): CategoryEvent()
    data class DishClick(val dishId: String): CategoryEvent()
    data class SortPick(val sortType: SortType): CategoryEvent()
    object NavigateUpClick: CategoryEvent()
}