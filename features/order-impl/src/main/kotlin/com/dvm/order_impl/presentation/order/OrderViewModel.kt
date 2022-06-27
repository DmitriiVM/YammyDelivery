package com.dvm.order_impl.presentation.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.order_impl.presentation.order.model.OrderEvent
import com.dvm.order_impl.presentation.order.model.OrderState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class OrderViewModel(
    orderId: String,
    private val orderInteractor: OrderInteractor,
    private val cartInteractor: CartInteractor,
    private val navigator: Navigator,
    datastore: DatastoreRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrderState())
        private set

    private val id = savedState.getLiveData(Destination.Order.ORDER_ID, orderId)

    init {
        val id = requireNotNull(id.value)

        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Order(id)))
            }
            .launchIn(viewModelScope)

        orderInteractor
            .order(id)
            .distinctUntilChanged()
            .onEach { state = state.copy(order = it) }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: OrderEvent) {
        when (event) {
            OrderEvent.CancelOrder -> {
                cancelOrder()
            }
            OrderEvent.TryOrderAgain -> {
                checkCart()
            }
            OrderEvent.OrderAgain -> {
                viewModelScope.launch {
                    cartInteractor.clearCart()
                    orderAgain()
                }
            }
            OrderEvent.DismissAlert -> {
                state = state.copy(
                    alert = null,
                    orderAgainMessage = null
                )
            }
            OrderEvent.CancelOrdering -> {
                navigator.back()
            }
            OrderEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun cancelOrder() {
        state = state.copy(progress = true)
        viewModelScope.launch {
            try {
                val orderId = requireNotNull(state.order?.id)
                orderInteractor.cancelOrder(orderId)

                state = state.copy(
                    progress = false,
                    cancelMessage = CoreR.string.order_message_order_canceled
                )
            } catch (exception: CancellationException) {
                throw CancellationException()
            } catch (exception: Exception) {
                state = state.copy(
                    progress = false,
                    alert = exception.getErrorMessage()
                )
            }
        }
    }

    private fun checkCart() {
        viewModelScope.launch {
            val cartCount = cartInteractor.getDishCount()
            if (cartCount == 0) {
                orderAgain()
                return@launch
            }
            state = state.copy(orderAgainMessage = OrderState.Message(cartCount))
        }
    }

    private fun orderAgain() {
        viewModelScope.launch {
            val orderItems = state.order?.items.orEmpty()
            val cartItems =
                orderItems
                    .map {
                        CartItem(
                            dishId = it.dishId,
                            quantity = it.amount
                        )
                    }
            cartInteractor.addToCart(cartItems)
            navigator.goTo(Destination.Cart)
        }
    }
}

