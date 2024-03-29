package com.dvm.menu_impl.presentation.category.presentation.model

import com.dvm.menu_api.domain.model.OrderType

internal sealed class CategoryEvent {
    data class ChangeSubcategory(val id: String) : CategoryEvent()
    data class OrderBy(val orderType: OrderType) : CategoryEvent()
    data class AddToCart(val dishId: String, val name: String) : CategoryEvent()
    data class OpenDish(val dishId: String) : CategoryEvent()
    object DismissAlert : CategoryEvent()
    object Back : CategoryEvent()
}