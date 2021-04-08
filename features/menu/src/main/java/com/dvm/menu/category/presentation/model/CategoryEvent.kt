package com.dvm.menu.category.presentation.model

internal sealed class CategoryEvent {
    data class AddToCart(val dishId: String): CategoryEvent()
    data class AddToFavorite(val dishId: String): CategoryEvent()
    data class ChangeSubcategory(val id: String): CategoryEvent()
    data class NavigateToDish(val dishId: String): CategoryEvent()
    data class Order(val orderType: OrderType): CategoryEvent()
    object DismissAlert: CategoryEvent()
    object NavigateUp: CategoryEvent()
}