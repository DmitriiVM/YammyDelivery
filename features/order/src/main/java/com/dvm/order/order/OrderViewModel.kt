package com.dvm.order.order

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.dvm.db.api.CartRepository
import com.dvm.db.api.OrderRepository
import com.dvm.db.api.models.CartItem
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.OrderApi
import com.dvm.order.R
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.updateservice.toDbEntity
import com.dvm.utils.StringProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class OrderViewModel(
    orderId: String,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderState())
        private set

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
            OrderEvent.OrderAgainClick -> {
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
                val order = orderApi.cancelOrder(
                    token = requireNotNull(datastore.getAccessToken()),
                    orderId = requireNotNull(state.order?.id)
                )
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
            val cartItems =
                orderItems
                    .map {
                        CartItem(
                            dishId = it.dishId,
                            quantity = it.amount
                        )
                    }
            cartRepository.addToCart(cartItems)
            navigator.goTo(Destination.Cart)
        }
    }
}

internal class OrderViewModelFactory @AssistedInject constructor(
    @Assisted private val orderId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(
                orderId = orderId,
                orderRepository = orderRepository,
                cartRepository = cartRepository,
                orderApi = orderApi,
                datastore = datastore,
                stringProvider = stringProvider,
                navigator = navigator
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface OrderViewModelAssistedFactory {
    fun create(
        orderId: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): OrderViewModelFactory
}