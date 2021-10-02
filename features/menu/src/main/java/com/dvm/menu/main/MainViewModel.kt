package com.dvm.menu.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.database.api.CartRepository
import com.dvm.database.api.DishRepository
import com.dvm.database.api.models.CartItem
import com.dvm.menu.R
import com.dvm.menu.search.model.MainEvent
import com.dvm.menu.search.model.MainState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val datastore: DatastoreRepository,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    dishRepository: DishRepository,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            if (datastore.isUpdateError()) {
                state = state.copy(
                    alert = MainState.Alert(R.string.main_message_update_error)
                )
                datastore.setUpdateError(false)
            }
        }

        combine(
            dishRepository.recommended(),
            dishRepository.best(),
            dishRepository.popular(),
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
                    cartRepository.addToCart(cartItem)
                }
                state = state.copy(
                    alert = MainState.Alert(
                        text = R.string.message_dish_added_to_cart,
                        argument = event.name
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