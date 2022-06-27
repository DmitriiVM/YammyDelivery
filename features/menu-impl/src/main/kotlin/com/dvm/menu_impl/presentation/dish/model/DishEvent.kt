package com.dvm.menu_impl.presentation.dish.model

internal sealed class DishEvent {

    data class SendReview(
        val rating: Int,
        val text: String
    ) : DishEvent()

    object AddToCart : DishEvent()
    object AddPiece : DishEvent()
    object RemovePiece : DishEvent()
    object ToggleFavorite : DishEvent()
    object AddReview : DishEvent()
    object DismissAlert : DishEvent()
    object DismissReviewDialog : DishEvent()
    object Back : DishEvent()
}