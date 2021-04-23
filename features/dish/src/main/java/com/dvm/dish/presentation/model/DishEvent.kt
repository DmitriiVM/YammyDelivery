package com.dvm.dish.presentation.model

internal sealed class DishEvent {
    object AddToCart: DishEvent()
    object AddPiece: DishEvent()
    object RemovePiece: DishEvent()
    object ToggleFavorite: DishEvent()
    object AddReview: DishEvent()
    object DismissAlert : DishEvent()
    object BackClick: DishEvent()
}