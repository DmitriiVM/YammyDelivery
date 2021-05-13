package com.dvm.order.ordering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.CartRepository
import com.dvm.db.api.OrderRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.OrderApi
import com.dvm.order.ordering.model.OrderingEvent
import com.dvm.order.ordering.model.OrderingFields
import com.dvm.order.ordering.model.OrderingState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.updateservice.toDbEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderingViewModel @Inject constructor(
    private val orderApi: OrderApi,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderingState())
        private set

    fun dispatchEvent(event: OrderingEvent) {
        when (event) {
            is OrderingEvent.MakeOrder -> {
                makeOrder(event.fields)
            }
            OrderingEvent.MapButtonClick -> {

            }
            OrderingEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            OrderingEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun makeOrder(fields: OrderingFields) {
        state = state.copy(networkCall = true)
        viewModelScope.launch {
            try {
                val order = orderApi.createOrder(
                    token = requireNotNull(datastore.getAccessToken()),
                    address = fields.address,
                    entrance = fields.entrance.toIntOrNull(),
                    floor = fields.floor.toIntOrNull(),
                    apartment = fields.apartment,
                    intercom = fields.intercom,
                    comment = fields.comment,
                )

                with(orderRepository){
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
                    networkCall = false,
                    alertMessage = exception.message
                )
            }
        }
    }
}