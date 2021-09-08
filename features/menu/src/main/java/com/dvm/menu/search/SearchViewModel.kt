package com.dvm.menu.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.CartRepository
import com.dvm.db.api.CategoryRepository
import com.dvm.db.api.DishRepository
import com.dvm.db.api.HintRepository
import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.Hint
import com.dvm.menu.R
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("StaticFieldLeak")
internal class SearchViewModel(
    private val context: Context,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val hintRepository: HintRepository,
    private val cartRepository: CartRepository,
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

        combine(
            query.asFlow(),
            hintRepository.hints()
        ) { query, hints ->
            state = state.copy(
                query = query,
                hints = hints
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: SearchEvent) {
        when (event) {
            is SearchEvent.OpenDish -> {
                saveHint(event.name)
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is SearchEvent.OpenCategory -> {
                saveHint(event.name)
                navigator.goTo(Destination.Category(event.categoryId))
            }
            is SearchEvent.OpenSubcategory -> {
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
                    hintRepository.delete(event.hint)
                }
            }
            is SearchEvent.AddToCart -> {
                viewModelScope.launch {
                    cartRepository.addToCart(
                        CartItem(
                            dishId = event.dishId,
                            quantity = 1
                        )
                    )
                    state = state.copy(
                        alert = String.format(
                            context.getString(
                                R.string.message_dish_added_to_cart,
                                event.name
                            )
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
            if (hintRepository.hintCount() >= 5) {
                hintRepository.deleteOldest()
            }
            hintRepository.insert(
                Hint(
                    query = name.trim(),
                    date = Date(System.currentTimeMillis())
                )
            )
        }
    }
}