package com.dvm.menu.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.CartRepository
import com.dvm.db.api.DishRepository
import com.dvm.menu.R
import com.dvm.menu.search.model.MainEvent
import com.dvm.menu.search.model.MainState
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val datastore: DatastoreRepository,
    private val dishRepository: DishRepository,
    private val cartRepository: CartRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            if (datastore.isUpdateError()) {
                state = state.copy(
                    alertMessage = stringProvider.getString(R.string.main_message_update_error)
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
            is MainEvent.DishClick -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            MainEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            MainEvent.BackClick -> {
                navigator.back()
            }
            is MainEvent.AddToCart -> {
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
            MainEvent.CartClick -> {
                navigator.goTo(Destination.Cart)
            }
            MainEvent.SeeAllClick -> {
                navigator.goTo(Destination.Menu)
            }
        }
    }
}