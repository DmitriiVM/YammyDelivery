package com.dvm.menu.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val navigator: Navigator,
    private val savedState: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    init {
        categoryRepository
            .hints()
            .onEach { hints ->
                state = state.copy(hints = hints)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: SearchEvent){

        when (event) {
            is SearchEvent.NavigateToDish -> {
                saveHint()
            }
            is SearchEvent.NavigateToCategory -> {
                saveHint()
            }
            is SearchEvent.QueryChange -> {
                state = state.copy(query = event.query)
            }
            SearchEvent.DismissAlert -> {

            }
            SearchEvent.NavigateUp -> {
                navigator.back()
            }
            is SearchEvent.HintClick -> {
                state = state.copy(query = event.hint)
            }
            is SearchEvent.RemoveHintClick -> {
                viewModelScope.launch {
                    categoryRepository.removeHint(event.hint)
                }
            }
        }
//            .exhaustive
    }

    private fun saveHint() {
        viewModelScope.launch {
            categoryRepository.saveHint(state.query)
        }
    }
}