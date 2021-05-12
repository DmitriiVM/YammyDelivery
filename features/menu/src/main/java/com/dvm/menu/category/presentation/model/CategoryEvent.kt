package com.dvm.menu.category.presentation.model

internal sealed class CategoryEvent {
    data class ChangeSubcategory(val id: String): CategoryEvent()
    data class OrderBy(val orderType: OrderType): CategoryEvent()
    data class AddToCart(val dishId: String): CategoryEvent()
    data class DishClick(val dishId: String): CategoryEvent()
    object DismissAlert: CategoryEvent()
    object BackClick: CategoryEvent()
}