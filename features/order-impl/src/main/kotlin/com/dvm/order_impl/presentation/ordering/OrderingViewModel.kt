package com.dvm.order_impl.presentation.ordering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.order_api.domain.OrderInteractor
import com.dvm.order_impl.presentation.ordering.model.OrderingEvent
import com.dvm.order_impl.presentation.ordering.model.OrderingFields
import com.dvm.order_impl.presentation.ordering.model.OrderingState
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class OrderingViewModel(
    private val orderInteractor: OrderInteractor,
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
                val orderId = orderInteractor.createOrder(
                    address = state.address,
                    entrance = fields.entrance.toIntOrNull(),
                    floor = fields.floor.toIntOrNull(),
                    apartment = fields.apartment,
                    intercom = fields.intercom,
                    comment = fields.comment,
                )

                navigator.goTo(Destination.Order(orderId.id))
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
}