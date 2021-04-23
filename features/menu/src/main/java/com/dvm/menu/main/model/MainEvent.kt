package com.dvm.menu.search.model

internal sealed class MainEvent {
    data class DishClick(val dishId: String) : MainEvent()
    data class AddToCart(val dishId: String, val name: String) : MainEvent()
    object CartClick : MainEvent()
    object SeeAllClick : MainEvent()
    object DismissAlert : MainEvent()
    object BackClick : MainEvent()
}