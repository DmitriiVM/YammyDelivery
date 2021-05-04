package com.dvm.menu.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.menu.R
import com.dvm.menu.search.model.FavoriteEvent
import com.dvm.menu.search.model.FavoriteState
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val cartRepository: CartRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
) : ViewModel() {

    var state by mutableStateOf(FavoriteState())
        private set

    init {
        dishRepository
            .favorite()
            .onEach { dishes ->
                state = state.copy(dishes = dishes)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddToCart -> {
                viewModelScope.launch {
                    cartRepository.addToCart(event.dishId, 1)
                }
                state = state.copy(
                    alertMessage = stringProvider.getString(
                        resId = R.string.message_dish_added_to_cart,
                        event.name
                    )
                )
            }
            is FavoriteEvent.DishClick -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            FavoriteEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
        }
    }
}