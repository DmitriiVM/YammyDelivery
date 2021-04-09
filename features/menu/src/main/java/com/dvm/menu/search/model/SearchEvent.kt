package com.dvm.menu.search.model

internal sealed class SearchEvent {
    data class QueryChange(val query: String): SearchEvent()
    data class HintClick(val hint: String): SearchEvent()
    data class RemoveHintClick(val hint: String): SearchEvent()
    data class NavigateToDish(val dishId: String): SearchEvent()
    data class NavigateToCategory(val dishId: String): SearchEvent()
    object DismissAlert: SearchEvent()
    object NavigateUp: SearchEvent()
}