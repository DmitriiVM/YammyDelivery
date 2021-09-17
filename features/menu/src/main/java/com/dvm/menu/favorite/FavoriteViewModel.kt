package com.dvm.menu.favorite

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.database.CartItem
import com.dvm.database.api.CartRepository
import com.dvm.database.api.DishRepository
import com.dvm.menu.R
import com.dvm.menu.favorite.model.FavoriteEvent
import com.dvm.menu.favorite.model.FavoriteState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class FavoriteViewModel(
    private val context: Context,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    dishRepository: DishRepository,
) : ViewModel() {

    var state by mutableStateOf(FavoriteState())
        private set

    init {
        dishRepository
            .favorite()
            .onEach { state = state.copy(dishes = it)}
            .launchIn(viewModelScope)
    }

    fun dispatch(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddToCart -> {
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
            is FavoriteEvent.OpenDish -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            FavoriteEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
        }
    }
}