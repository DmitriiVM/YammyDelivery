package com.dvm.menu.search.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class SearchState(
    val query: String = "",
    val hints: List<String> = emptyList(),
    val alertMessage: String? = null
)