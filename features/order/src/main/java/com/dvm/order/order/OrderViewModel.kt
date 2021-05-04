package com.dvm.order.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.OrderRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.network_api.api.OrderApi
import com.dvm.order.R
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.updateservice.toDbEntity
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrderState())
        private set

    private val orderId = requireNotNull(savedState.get<String>("orderId"))

    init {
        orderRepository
            .order(orderId)
            .distinctUntilChanged()
            .onEach { state = state.copy(order = it) }
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: OrderEvent) {
        when (event) {
            OrderEvent.CancelOrder -> {
                cancelOrder()
            }
            OrderEvent.ReorderClick -> {
                checkCart()
            }
            OrderEvent.OrderAgain -> {
                viewModelScope.launch {
                    cartRepository.clearCart()
                    orderAgain()
                }
            }
            OrderEvent.DismissAlert -> {
                state = state.copy(
                    alertMessage = null,
                    actionAlertMessage = null
                )
            }
            OrderEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun cancelOrder() {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                val orderId = requireNotNull(state.order?.id)
                val order = orderApi.cancelOrder(orderId)
                orderRepository.insertOrders(listOf(order.toDbEntity()))

                state = state.copy(
                    networkCall = false,
                    alertMessage = stringProvider.getString(R.string.order_message_order_canceled)
                )
            } catch (exception: Exception) {
                state = state.copy(
                    networkCall = false,
                    alertMessage = exception.message
                )
            }
        }
    }

    private fun checkCart() {
        viewModelScope.launch {
            val cartCount = cartRepository.getCount()
            if (cartCount == 0) {
                orderAgain()
                return@launch
            }
            state = state.copy(
                actionAlertMessage = stringProvider.getString(
                    R.string.order_message_cart_not_empty,
                    stringProvider.getQuantityString(
                        R.plurals.order_message_plural_dish,
                        cartCount
                    )
                )
            )
        }
    }

    private fun orderAgain() {
        viewModelScope.launch {
            val orderItems = state.order?.items.orEmpty()
            val cartItems = orderItems.map { it.dishId to it.amount }
            cartRepository.addToCart(cartItems)
            navigator.goTo(Destination.Cart)
        }
    }
}