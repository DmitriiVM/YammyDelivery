package com.dvm.dish.model

sealed class DishNavigationEvent{
    object Up: DishNavigationEvent()
}