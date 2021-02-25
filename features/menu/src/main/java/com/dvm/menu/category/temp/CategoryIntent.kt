package com.dvm.menu.category.temp

sealed class CategoryIntent {
    data class SubcategoryClick(val id: String): CategoryIntent()
    data class AddToCartClick(val dishId: String): CategoryIntent()
    data class DishClick(val dishId: String): CategoryIntent()
    data class SortPick(val type: SortType): CategoryIntent()
    data class SortClick(val isShown: Boolean): CategoryIntent()
    object NavigateUpClick: CategoryIntent()
}
