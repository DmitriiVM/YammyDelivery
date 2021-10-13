package com.dvm.menu.main.model

internal sealed class MainEvent {
    data class AddToCart(val dishId: String, val name: String) : MainEvent()
    data class OpenDish(val dishId: String) : MainEvent()
    object OpenCart : MainEvent()
    object SeeAll : MainEvent()
    object DismissAlert : MainEvent()
    object Back : MainEvent()
}