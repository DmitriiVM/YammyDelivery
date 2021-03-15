package com.dvm.menu.category.presentation.model

sealed class NavigationEvent {
    object Up: NavigationEvent()
    data class ToDetails(val dishId: String): NavigationEvent()
}