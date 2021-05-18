package com.dvm.order.order

import android.annotation.SuppressLint
import android.content.Context
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
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class OrderViewModel(
    orderId: String,
    private val context: Context,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderState())
        private set

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Order(orderId)))
            }
            .launchIn(viewModelScope)

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
                    alertMessage = context.getString(R.string.order_message_order_canceled)
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
            val cartCount = cartRepository.getDishCount()
            if (cartCount == 0) {
                orderAgain()
                return@launch
            }
            state = state.copy(
                actionAlertMessage = context.getString(
                    R.string.order_message_cart_not_empty,
                    context.resources.getQuantityString(
                        R.plurals.order_message_plural_dish,
                        cartCount,
                        cartCount,
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
    @ApplicationContext private val context: Context,
    @Assisted private val orderId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
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
                context = context,
                orderRepository = orderRepository,
                cartRepository = cartRepository,
                orderApi = orderApi,
                datastore = datastore,
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