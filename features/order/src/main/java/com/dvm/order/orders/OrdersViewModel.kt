package com.dvm.order.orders

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.OrderRepository
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.order.orders.model.OrderStatus
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.updateservice.api.UpdateService
import com.dvm.utils.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class OrdersViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val orderRepository: OrderRepository,
    private val updateService: UpdateService,
    private val navigator: Navigator,
    datastore: DatastoreRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrdersState())
        private set

    private val status = savedState.getLiveData("orders_status", OrderStatus.ACTUAL)

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Orders))
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            state = state.copy(progress = true)
            try {
                updateService.updateOrders()
            } catch (exception: Exception) {
                state = state.copy(alert = exception.getErrorMessage(context))
            } finally {
                state = state.copy(progress = false)
            }

            status
                .asFlow()
                .distinctUntilChanged()
                .flatMapLatest { status ->
                    when (status) {
                        OrderStatus.ACTUAL -> orderRepository.activeOrders()
                        OrderStatus.COMPLETED -> orderRepository.completedOrders()
                    }
                        .map { orders ->
                            status to orders
                        }
                }
                .collect { (status, orders) ->
                    state = state.copy(
                        status = status,
                        orders = orders
                    )
                }
        }
    }

    fun dispatch(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.Order -> {
                navigator.goTo(Destination.Order(event.orderId))
            }
            is OrdersEvent.StatusSelect -> {
                status.value = event.status
            }
            OrdersEvent.Back -> {
                navigator.back()
            }
        }
    }
}