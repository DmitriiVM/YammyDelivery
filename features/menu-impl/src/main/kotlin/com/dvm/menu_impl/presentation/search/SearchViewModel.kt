package com.dvm.menu_impl.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.HintInteractor
import com.dvm.menu_api.domain.model.Hint
import com.dvm.menu_impl.presentation.search.model.SearchEvent
import com.dvm.menu_impl.presentation.search.model.SearchState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.Text
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import com.dvm.ui.R as CoreR

internal class SearchViewModel(
    private val categoryInteractor: CategoryInteractor,
    private val dishInteractor: DishInteractor,
    private val hintInteractor: HintInteractor,
    private val cartInteractor: CartInteractor,
    private val navigator: Navigator,
    savedState: SavedStateHandle
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
                    dishInteractor.search(query),
                    categoryInteractor.searchParentCategory(query),
                    categoryInteractor.searchSubcategory(query),
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

        query
            .asFlow()
            .onEach {
                state = state.copy(query = it)
            }
            .launchIn(viewModelScope)

        hintInteractor
            .hints()
            .onEach {
                state = state.copy(hints = it)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: SearchEvent) {
        when (event) {
            is SearchEvent.OpenDish -> {
                state = state.copy(query = "")
                saveHint(event.name)
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is SearchEvent.OpenCategory -> {
                state = state.copy(query = "")
                saveHint(event.name)
                navigator.goTo(Destination.Category(event.categoryId))
            }
            is SearchEvent.OpenSubcategory -> {
                state = state.copy(query = "")
                saveHint(event.name)
                navigator.goTo(
                    Destination.Category(
                        categoryId = event.categoryId,
                        subcategoryId = event.subcategoryId
                    )
                )
            }
            is SearchEvent.ChangeQuery -> {
                query.value = event.query
            }
            is SearchEvent.SelectHint -> {
                query.value = event.hint
            }
            is SearchEvent.RemoveHint -> {
                viewModelScope.launch {
                    hintInteractor.delete(event.hint)
                }
            }
            is SearchEvent.AddToCart -> {
                viewModelScope.launch {
                    cartInteractor.addToCart(
                        CartItem(
                            dishId = event.dishId,
                            quantity = 1
                        )
                    )
                    state = state.copy(
                        alert = Text.Resource(
                            resId = CoreR.string.message_dish_added_to_cart,
                            formatArgs = listOf(event.name)
                        )
                    )
                }
            }
            SearchEvent.RemoveQuery -> {
                state = state.copy(query = "")
            }
            SearchEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            SearchEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun saveHint(name: String) {
        viewModelScope.launch {
            hintInteractor.saveHint(
                Hint(
                    query = name.trim(),
                    date = Date(System.currentTimeMillis())
                )
            )
        }
    }
}