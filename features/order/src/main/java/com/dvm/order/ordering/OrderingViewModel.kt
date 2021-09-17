package com.dvm.order.ordering

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.database.api.CartRepository
import com.dvm.database.api.OrderRepository
import com.dvm.database.api.mappers.toDbEntity
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.OrderApi
import com.dvm.order.ordering.model.OrderingEvent
import com.dvm.order.ordering.model.OrderingFields
import com.dvm.order.ordering.model.OrderingState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class OrderingViewModel(
    private val context: Context,
    private val orderApi: OrderApi,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderingState())
        private set

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Ordering))
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: OrderingEvent) {
        when (event) {
            is OrderingEvent.ChangeAddress -> {
                state = state.copy(address = event.address)
            }
            is OrderingEvent.MakeOrder -> {
                makeOrder(event.fields)
            }
            OrderingEvent.OpenMap -> {
                navigator.goTo(Destination.Map())
            }
            OrderingEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            OrderingEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun makeOrder(fields: OrderingFields) {
        state = state.copy(progress = true)
        viewModelScope.launch {
            try {
                val order = orderApi.createOrder(
                    token = requireNotNull(datastore.getAccessToken()),
                    address = state.address,
                    entrance = fields.entrance.toIntOrNull(),
                    floor = fields.floor.toIntOrNull(),
                    apartment = fields.apartment,
                    intercom = fields.intercom,
                    comment = fields.comment,
                )

                with(orderRepository) {
                    insertOrders(listOf(order.toDbEntity()))
                    insertOrderItems(
                        order.items.map { it.toDbEntity(order.id) }
                    )
                    insertOrderStatuses(
                        orderApi
                            .getStatuses(datastore.getLastUpdateTime())
                            .map { it.toDbEntity() }
                    )
                }
                cartRepository.clearCart()

                navigator.goTo(Destination.Order(order.id))
            } catch (exception: Exception) {
                state = state.copy(
                    progress = false,
                    alert = exception.getErrorMessage(context)
                )
            }
        }
    }
}