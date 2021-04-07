package com.dvm.menu.category.presentation.model

internal sealed class CategoryNavigationEvent {
    object Up: CategoryNavigationEvent()
    data class ToDetails(val dishId: String): CategoryNavigationEvent()
}