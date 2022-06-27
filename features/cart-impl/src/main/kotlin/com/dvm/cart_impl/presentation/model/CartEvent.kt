package com.dvm.cart_impl.presentation.model

internal sealed class CartEvent {
    data class ChangePromoCode(val text: String) : CartEvent()
    data class OpenDish(val dishId: String) : CartEvent()
    data class AddPiece(val dishId: String) : CartEvent()
    data class RemovePiece(val dishId: String) : CartEvent()
    object CreateOrder : CartEvent()
    object ApplyPromoCode : CartEvent()
    object CancelPromoCode : CartEvent()
    object DismissAlert : CartEvent()
    object Back : CartEvent()
}