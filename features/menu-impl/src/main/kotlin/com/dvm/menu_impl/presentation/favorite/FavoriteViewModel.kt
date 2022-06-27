package com.dvm.menu_impl.presentation.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_impl.presentation.favorite.model.FavoriteEvent
import com.dvm.menu_impl.presentation.favorite.model.FavoriteState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.Text
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class FavoriteViewModel(
    private val cartInteractor: CartInteractor,
    private val navigator: Navigator,
    dishInteractor: DishInteractor,
) : ViewModel() {

    var state by mutableStateOf(FavoriteState())
        private set

    init {
        dishInteractor
            .favorite()
            .onEach { state = state.copy(dishes = it) }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddToCart -> {
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
            is FavoriteEvent.OpenDish -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            FavoriteEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
        }
    }
}