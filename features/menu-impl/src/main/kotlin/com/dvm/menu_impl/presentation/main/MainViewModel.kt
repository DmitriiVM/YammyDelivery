package com.dvm.menu_impl.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_impl.presentation.main.model.MainEvent
import com.dvm.menu_impl.presentation.search.model.MainState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.Text
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class MainViewModel(
    private val datastore: DatastoreRepository,
    private val cartInteractor: CartInteractor,
    private val navigator: Navigator,
    dishInteractor: DishInteractor,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            if (datastore.isUpdateError()) {
                state = state.copy(
                    alert = Text.Resource(CoreR.string.main_message_update_error)
                )
                datastore.setUpdateError(false)
            }
        }

        combine(
            dishInteractor.recommended(),
            dishInteractor.best(),
            dishInteractor.popular(),
        ) { recommended, best, popular ->
            state = state.copy(
                recommended = recommended.shuffled(),
                best = if (best.size < 2) emptyList() else best.shuffled(),
                popular = popular.shuffled()
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: MainEvent) {
        when (event) {
            is MainEvent.AddToCart -> {
                viewModelScope.launch {
                    val cartItem = CartItem(event.dishId, 1)
                    cartInteractor.addToCart(cartItem)
                }
                state = state.copy(
                    alert = Text.Resource(
                        resId = CoreR.string.message_dish_added_to_cart,
                        formatArgs = listOf(event.name)
                    )
                )
            }
            is MainEvent.OpenDish -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            MainEvent.OpenCart -> {
                navigator.goTo(Destination.Cart)
            }
            MainEvent.SeeAll -> {
                navigator.goTo(Destination.Menu)
            }
            MainEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            MainEvent.Back -> {
                navigator.back()
            }
        }
    }
}