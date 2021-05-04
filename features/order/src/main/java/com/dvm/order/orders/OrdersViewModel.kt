package com.dvm.order.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.OrderRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.network_api.api.OrderApi
import com.dvm.order.orders.model.OrderStatus
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.updateservice.UpdateService
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
    private val updateService: UpdateService,
    private val stringProvider: StringProvider,
    private val appScope: CoroutineScope,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrdersState())
        private set

    private val status = savedState.getLiveData("orders_status", OrderStatus.ACTUAL)

    init {
        viewModelScope.launch {
//            if (!datastore.isAuthorized()) {
//                navigator.goTo(Destination.Auth)
//            }
        }
//        appScope.launch {
//            updateService.updateOrders()
//        }

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
            .onEach { (status, orders) ->
                state = state.copy(
                    status = status,
                    orders = orders
                )
            }
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: OrdersEvent) {
        when (event) {
            OrdersEvent.BackClick -> {
                navigator.back()
            }
            is OrdersEvent.SelectStatus -> {
                status.value = event.status
            }
            is OrdersEvent.OrderClick -> {
                navigator.goTo(Destination.Order(event.orderId))
            }
        }
    }
}