package com.dvm.cart.model

internal sealed class CartEvent {
    data class PromoCodeTextChanged(val text: String): CartEvent()
    data class DishClick(val dishId: String): CartEvent()
    data class AddPiece(val dishId: String): CartEvent()
    data class RemovePiece(val dishId: String): CartEvent()
    object CreateOrder : CartEvent()
    object ApplyPromoCode: CartEvent()
    object CancelPromoCode: CartEvent()
    object DismissAlert : CartEvent()
    object BackClick: CartEvent()
}