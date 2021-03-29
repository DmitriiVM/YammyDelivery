package com.dvm.dish.dish_impl.presentation.model

internal sealed class DishEvent {
    object AddToCart: DishEvent()
    object AddPiece: DishEvent()
    object RemovePiece: DishEvent()
    object ChangeFavorite: DishEvent()
    object AddReview: DishEvent()
    object NavigateUp: DishEvent()
}