package com.dvm.menu.category.temp

sealed class CategoryAction {
    data class SubcategoryClick(val id: String): CategoryAction()
    data class AddToCartClick(val dishId: String): CategoryAction()
    data class DishClick(val dishId: String): CategoryAction()
    data class SortPick(val type: SortType): CategoryAction()
    data class SortClick(val isShown: Boolean): CategoryAction()
    object NavigateUpClick: CategoryAction()
}