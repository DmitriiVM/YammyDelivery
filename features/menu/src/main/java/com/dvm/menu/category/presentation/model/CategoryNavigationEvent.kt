package com.dvm.menu.category.presentation.model

sealed class CategoryNavigationEvent {
    object Up: CategoryNavigationEvent()
    data class ToDetails(val dishId: String): CategoryNavigationEvent()
}