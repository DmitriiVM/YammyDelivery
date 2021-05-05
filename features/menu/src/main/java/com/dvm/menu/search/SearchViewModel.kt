package com.dvm.menu.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.data.CategoryRepository
import com.dvm.db.api.data.DishRepository
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val navigator: Navigator,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val query = savedState.getLiveData("search_query", "")

    init {
        query
            .asFlow()
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .debounce(500)
            .flatMapLatest { query ->
                combine(
                    dishRepository.search(query),
                    categoryRepository.searchParentCategory(query),
                    categoryRepository.searchSubcategory(query),
                ) { dishes, categories, subcategories ->
                    Triple(dishes, categories, subcategories)
                }
            }
            .onEach { (dishes, categories, subcategories) ->
                state = state.copy(
                    dishes = dishes,
                    categories = categories,
                    subcategories = subcategories,
                )
            }
            .launchIn(viewModelScope)

        categoryRepository
            .hints()
            .onEach { hints ->
                state = state.copy(hints = hints)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: SearchEvent) {

        when (event) {
            is SearchEvent.DishClick -> {
                saveHint()
            }
            is SearchEvent.CategoryClick -> {
                saveHint()
            }
            is SearchEvent.QueryChange -> {
                query.value = event.query
                state = state.copy(query = event.query)
            }
            SearchEvent.DismissAlert -> {

            }
            SearchEvent.BackClick -> {
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