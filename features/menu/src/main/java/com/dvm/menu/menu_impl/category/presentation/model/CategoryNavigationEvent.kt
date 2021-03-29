package com.dvm.menu.menu_impl.category.presentation.model

internal sealed class CategoryNavigationEvent {
    object Up: CategoryNavigationEvent()
    data class ToDetails(val dishId: String): CategoryNavigationEvent()
}