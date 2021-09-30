package com.dvm.dish

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.database.CartItem
import com.dvm.database.api.CartRepository
import com.dvm.database.api.DishRepository
import com.dvm.database.api.FavoriteRepository
import com.dvm.dish.model.DishEvent
import com.dvm.dish.model.DishState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.MenuApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal class DishViewModel(
    _dishId: String,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
    dishRepository: DishRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DishState())
        private set

    private val dishId = savedState.getLiveData(Destination.Dish.DISH_ID, _dishId)
    private val quantity = savedState.getLiveData("quantity", 1)
    private val id: String
        get() = requireNotNull(dishId.value)

    init {
        combine(
            dishRepository.dish(id),
            quantity.asFlow()
        ) { dish, quantity ->
            state = state.copy(
                dish = dish,
                quantity = quantity
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: DishEvent) {
        viewModelScope.launch {
            when (event) {
                DishEvent.AddToCart -> {
                    addToCart()
                }
                DishEvent.RemovePiece -> {
                    val newValue = quantity.value?.minus(1)
                    quantity.value = newValue?.coerceAtLeast(1)
                }
                DishEvent.AddPiece -> {
                    quantity.value = quantity.value?.plus(1)
                }
                DishEvent.ToggleFavorite -> {
                    toggleFavorite()
                }
                DishEvent.DismissAlert -> {
                    state = state.copy(alert = null)
                }
                DishEvent.Back -> {
                    navigator.back()
                }
                DishEvent.AddReview -> {
                    if (datastore.isAuthorized()) {
                        state = state.copy(reviewDialog = true)
                    } else {
                        state = state.copy(
                            alert = R.string.dish_message_unauthorized_review
                        )
                    }
                }
                DishEvent.DismissReviewDialog -> {
                    state = state.copy(reviewDialog = false)
                }
                is DishEvent.SendReview -> {
                    sendReview(
                        rating = event.rating,
                        text = event.text
                    )
                }
            }
        }
    }

    private suspend fun addToCart() {
        cartRepository.addToCart(
            CartItem(
                dishId = id,
                quantity = state.quantity
            )
        )
        state = state.copy(
            quantity = 1,
            alert = R.string.dish_message_added_to_cart
        )
    }

    private suspend fun toggleFavorite() {

        val currentIsFavorite = favoriteRepository.isFavorite(id)
        if (currentIsFavorite) {
            favoriteRepository.deleteFromFavorite(id)
        } else {
            favoriteRepository.addToFavorite(id)
        }

        if (!datastore.isAuthorized()) return
        try {
            menuApi.changeFavorite(
                token = requireNotNull(datastore.getAccessToken()),
                favorites = mapOf(id to !currentIsFavorite)
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Can't change favorite status: $exception")
        }

    }

    private fun sendReview(
        rating: Int,
        text: String
    ) {
        viewModelScope.launch {
            state = state.copy(progress = true)

            try {
                menuApi.addReview(
                    token = requireNotNull(datastore.getAccessToken()),
                    dishId = id,
                    rating = rating,
                    text = text,
                )
                state = state.copy(
                    progress = false,
                    reviewDialog = false,
                    alert = R.string.dish_message_review_result
                )
            } catch (exception: Exception) {
                state = state.copy(
                    alert = exception.getErrorMessage(),
                    progress = false
                )
            }
        }
    }

    companion object {
        private const val TAG = "DishViewModel"
    }
}