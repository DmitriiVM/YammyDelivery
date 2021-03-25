package com.dvm.dish.model

sealed class DishEvent {
    object AddToCart: DishEvent()
    object AddPiece: DishEvent()
    object RemovePiece: DishEvent()
    object ChangeFavorite: DishEvent()
    object AddReview: DishEvent()
    object NavigateUp: DishEvent()
}