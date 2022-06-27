package com.dvm.menu_impl.presentation.dish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.FavoriteInteractor
import com.dvm.menu_api.domain.ReviewInteractor
import com.dvm.menu_impl.presentation.dish.model.DishEvent
import com.dvm.menu_impl.presentation.dish.model.DishState
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class DishViewModel(
    dishId: String,
    private val favoriteInteractor: FavoriteInteractor,
    private val reviewInteractor: ReviewInteractor,
    private val cartInteractor: CartInteractor,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
    dishInteractor: DishInteractor,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DishState())
        private set

    private val dishIdLiveData = savedState.getLiveData(Destination.Dish.DISH_ID, dishId)
    private val quantity = savedState.getLiveData("quantity", 1)
    private val id: String
        get() = requireNotNull(dishIdLiveData.value)

    init {
        combine(
            dishInteractor.dish(id),
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
                            alert = CoreR.string.dish_message_unauthorized_review
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
        cartInteractor.addToCart(
            CartItem(
                dishId = id,
                quantity = state.quantity
            )
        )
        state = state.copy(
            quantity = 1,
            alert = CoreR.string.dish_message_added_to_cart
        )
    }

    private suspend fun toggleFavorite() {
        favoriteInteractor.toggleFavorite(id)
    }

    private fun sendReview(
        rating: Int,
        text: String
    ) {
        viewModelScope.launch {
            state = state.copy(progress = true)

            try {
                reviewInteractor.addReview(
                    token = requireNotNull(datastore.getAccessToken()),
                    dishId = id,
                    rating = rating,
                    text = text,
                )
                state = state.copy(
                    progress = false,
                    reviewDialog = false,
                    alert = CoreR.string.dish_message_review_result
                )
            } catch (exception: CancellationException) {
                throw CancellationException()
            } catch (exception: Exception) {
                state = state.copy(
                    alert = exception.getErrorMessage(),
                    progress = false
                )
            }
        }
    }
}